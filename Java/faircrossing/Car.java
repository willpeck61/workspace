package exercise2;

/**
 * Creates a Car to interact with 
 * Zebra Crossing
 * @author Will Peck, 159029051
 */

public class Car implements Runnable {
  private static int carid = 0;
  private int curid;
  private String dir;
  private ZebraCrossing zeb;
  private boolean heading;
  private int delay;

  /**
   * Constructor for Car.
   * @param zc - ZebraCrossing 
   * @param d - Delay (ms)
   * @param h - Heading (up = true, down = false)
   */
  public Car(ZebraCrossing zc, int d, boolean h) {
    this.zeb = zc;
    this.heading = h;
    this.delay = d;
    Car.carid += 1;
    curid = Car.carid;
  }

  /**
   * Returns the direction of the car. 
   * (up = true, down = false)
   * @return true is up, false is down
   */
  public boolean getHeading() {
    if (heading == true) {
      dir = "Up";
      return true;
    } else {
      dir = "Down";
      return false;
    }
  }
  
  /**
   * Run method passes itself to ZebraCrossing. 
   * Time taken to cross is simulated by sleeping for
   * given delay before finishing crossing.
   */
  @Override
  public void run() {
    zeb.arrive(this);
    System.out.println(toString() + " has arrived to go " + dir);
    zeb.startCrossing(this);
    System.out.println(toString() + " has started to drive " + dir);
    try {
      Thread.sleep(150 * delay);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println(toString() + " has finished driving " + dir);
    zeb.finishCrossing(this);
  }
  
  /**
   * Returns Car id string for output.
   * eg. C12
   */
  public String toString() {
    String car;
    if (getHeading() == true) {
      car = "C^" + curid;
    } else {
      car = "Cv" + curid;
    }
    return car;
  }
}
