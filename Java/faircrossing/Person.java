package exercise2;

/**
 * Creates a Person to arrive at the
 * ZebraCrossing.
 * Student No: 159029051
 * @author Will Peck
 *
 */
public class Person implements Runnable {
  
  private static int personid = 0;
  private int curid; 
  private int delay;
  private ZebraCrossing zeb;
  
  /**
   * Constructor.
   * @param zc ZebraCrossing
   * @param d Delay (ms)
   */
  public Person(ZebraCrossing zc, int d) {
    Person.personid += 1;
    curid = Person.personid;
    this.delay = d;
    this.zeb = zc;
  }
  
  /**
   * Run method passes itself to ZebraCrossing. 
   * Time taken to cross is simulated by sleeping for
   * given delay before finishing crossing.
   */
  public void run() {
    zeb.arrive(this);
    System.out.println(toString() + " has arrived.");
    zeb.startCrossing(this);
    System.out.println(toString() + " has started to cross.");
    try {
      Thread.sleep(500 * delay);
      System.out.println(toString() + " has finished crossing.");
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    zeb.finishCrossing(this);
  }
  
  /**
   * Returns a string for output
   * with the current person id.
   * eg. P1
   */
  public String toString() {
    String person = "P" + curid;
    return person;
  }
}
