package exercise2;

/**
 * Car handler class.
 * @author Will Peck, 159029051
 */

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;

public class CarHandler implements Runnable {
  private ThreadPoolExecutor thr;
  private ZebraCrossing zeb;
  private String filepath;
  private boolean heading;
  
  /**
   * Constructor.
   * @param e ThreadPoolExecutor
   * @param z ZebraCrossing
   * @param f Path to text file
   * @param h Heading (up = true, down = false)
   */
  public CarHandler(ThreadPoolExecutor e, ZebraCrossing z, String f, boolean h) {
    this.thr = e;
    this.zeb = z;
    this.filepath = f;
    this.heading = h;
  }
  
  /**
   * Run method creates Car based on parameter
   * from text file, sleeps for given time then 
   * adds Car to thread pool.
   */
  public void run() {
    Path carpath = Paths.get(filepath);
    try (Scanner file = new Scanner(carpath)) {
      int pause;
      int delay;
      while (file.hasNextLine()) {
        Scanner line = new Scanner(file.nextLine());
        line.useDelimiter(":");
        pause = line.nextInt();
        delay = line.nextInt();
        line.close();
        Thread.sleep(pause * 100);
        Car car = new Car(zeb, delay, heading);
        thr.execute(car);
        System.out.printf("pause: %s, delay %s\n", pause, delay);
      }
      file.close();
    } catch (NoSuchFileException e) {
      System.err.println("File not found: " + carpath);
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
