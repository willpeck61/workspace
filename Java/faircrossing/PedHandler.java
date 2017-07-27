package exercise2;

/**
 * Person Handler class.
 * @author Will Peck, 159029051
 */
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;

public class PedHandler implements Runnable {
  private String filepath;
  private ZebraCrossing zeb;
  private ThreadPoolExecutor thr;

  /**
   * Constructor.
   * @param e ThreadPoolExecutor object
   * @param z ZebraCrossing object
   * @param f Path to pedestrian text file.
   */
  public PedHandler(ThreadPoolExecutor e, ZebraCrossing z, String f){
    this.filepath = f;
    this.zeb = z;
    this.thr = e;
  }
  
  /**
   * Run method creates Person based on parameter
   * from text file, sleeps for given time then 
   * adds Person to thread pool.
   */
  public void run() {
    Path p1path = Paths.get(filepath);
    try (Scanner file = new Scanner(p1path)) {
      int pause;
      int delay;
      while (file.hasNextLine()) {
        Scanner line = new Scanner(file.nextLine());
        line.useDelimiter(":");
        pause = line.nextInt();
        delay = line.nextInt();
        line.close();
        try {
          Thread.sleep(pause * 100);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } 
        Person person = new Person(zeb, delay);
        thr.execute(person);
        System.out.printf("pause: %s, delay %s\n", pause, delay);
      }
      file.close();
    } catch (NoSuchFileException e) {
      System.err.println("File not found: " + p1path);
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e);
    }
  }
}