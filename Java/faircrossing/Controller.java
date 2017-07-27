package exercise2;

/**
 * Controller to monitor interactions
 * between objects across threads.
 * @author Will Peck, 159029051
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Controller {

  private static ZebraCrossing zeb;
  private static ThreadPoolExecutor thr;
  private static Controller controller;
  
  public Controller() {
    
  }
  
  /**
   * Main method takes command line input of text files
   * stored in /resources folder and passed them
   * to the relevant handler.
   * Author: Will Peck
   * Student No: 159029051
   * @param args - text files: persons data, 
   *     car travelling up data, car travelling down data
   */
  public static void main(String[] args) {
    if ( args.length != 3 ) {
      System.err.println("Error: Incorrect number of parameters.");
      System.exit(0);
    }
    String p1data = args[0] + ".txt";
    String cup1data = args[1] + ".txt";
    String cdown1data = args[2] + ".txt";
    zeb = new FairZebraCrossing();
    //zeb = new ZebraCrossing();
    thr = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    CarHandler cup = new CarHandler(thr, zeb, cdown1data, false);
    CarHandler cdown = new CarHandler(thr, zeb, cup1data, true);
    PedHandler ped = new PedHandler(thr, zeb, p1data);
    thr.execute(cup);
    thr.execute(cdown);
    thr.execute(ped);
    controller = new Controller();
    controller.run();
  }
  
  /**
   * Run method triggers ThreadPoolExecuter every
   * 1 second, checks the state of the crossing
   * and prints to console if state change is true.
   */
  public void run() {
    while (thr.getActiveCount() > 0) {
      try { 
        Thread.sleep(1000);
        if (zeb._changed == true) {
          zeb.toString();
        } 
      } catch (InterruptedException e) { 
        e.printStackTrace();
      }
    }
    zeb.toString();
    try {
      thr.shutdown();
      thr.awaitTermination(5L, TimeUnit.SECONDS);
      if (thr.isTerminated()) {
        System.out.println("All threads terminated.");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
