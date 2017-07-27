package exercise2;

/**
 * ZebraCrossing
 * Author:  Will Peck
 * Student No: 159029051
 */
import java.util.HashSet;
import java.util.Set;

public class ZebraCrossing {
  protected Set<Car> _carsInCrossingDown;  //Set of Car instances currently crossing Down
  protected Set<Car> _carsInCrossingUp; //Set of Car instances currently crossing Up
  protected int _carsWaitingDown; //Number of cars that have arrived but not started to cross
  protected int _carsWaitingUp; //Number of cars that have arrived but not started to cross
  protected boolean _changed; //true if the state has changed since the last call to toString()
  protected Set<Person> _pedestriansInCrossing; //Set of Person instances currently crossing
  protected int _pedsWaiting; //Number of pedestrians who have arrived but not started to cross
  
  /**
   * Contructor - Initialize fields.
   */
  public ZebraCrossing() {
    this._carsInCrossingDown = new HashSet<Car>();
    this._carsInCrossingUp = new HashSet<Car>();
    this._pedestriansInCrossing = new HashSet<Person>();
    this._changed = false;
  }
  
  /**
   * Record the arrival of a car that will want to cross
   * @param c - Car that has arrived.
   */
  public void arrive(Car c) {
    if (c.getHeading() == true) {
      _carsWaitingUp++;
    } else {
      _carsWaitingDown++;
    }
    isChanged();
    System.out.println(toString());
  }
  
  /**
   * Record the arrival of a person who will want to cross.
   * @param p - Person that has arrived.
   */
  public void arrive(Person p) {
    _pedsWaiting++;
    isChanged();
    System.out.println(toString());
  }
  
  /**
   * Record that a car has finished crossing.
   * @param c - Car has finished crossing
   */
  public synchronized void finishCrossing(Car c) {
    if (c.getHeading() == true) {
      _carsInCrossingUp.remove(c);
    } else {
      _carsInCrossingDown.remove(c);
    }
    isChanged();
    System.out.println(toString());
    notifyAll();
  }
  
  /**
   * Record that a pedestrian has finished crossing the road
   * @param p - Person to finish crossing.
   */
  public synchronized void finishCrossing(Person p) {
    _pedestriansInCrossing.remove(p);
    System.out.println(toString());
    isChanged();
    notifyAll();
  }
  
  /**
   * True if the state has changed since the last call to toString()
   * @return true - state has changed.
   */
  public synchronized boolean isChanged() {
    _changed = true;
    return _changed;
  }
  
  /**
   * Car travelling in x direction will 
   * start crossing if no pedestrians are on crossing.
   * Method triggers state change.
   * @param c - Car is about to cross
   */
  public synchronized void startCrossing(Car c) {
    if (c.getHeading() == true) {
      while (_pedestriansInCrossing.size() > 0 || _carsInCrossingUp.size() > 0) {
        try {
          wait();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    } else if (c.getHeading() == false) {
      while (_pedestriansInCrossing.size() > 0 || _carsInCrossingDown.size() > 0) {
        try {
          wait();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    if (c.getHeading() == true && _carsInCrossingUp.size() == 0) {
      _carsWaitingUp--;
      _carsInCrossingUp.add(c);
      System.out.println(toString());
      isChanged();
    } else if (_carsInCrossingDown.size() == 0) {
      _carsWaitingDown--;
      _carsInCrossingDown.add(c);
      System.out.println(toString());
      isChanged();
    }
  }
  
  /**
   * If no Cars are crossing and no more than 
   * 5 Persons then start crossing.
   * Method triggers state change
   * @param p - Person is about to start crossing.
   */
  public synchronized void startCrossing(Person p) {
    while ( _carsInCrossingUp.size() > 0 || _carsInCrossingDown.size() > 0 
        || _pedestriansInCrossing.size() >= 5 ) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    _pedestriansInCrossing.add(p);
    _pedsWaiting--;
    System.out.println(toString());
    isChanged();
  }
  
  /**
   * Returns string in console.
   * eg. Cars: ^[] W=3 v[] w=2
   *     Peds: [P1, P2] W=0
   */
  public synchronized String toString() {
    String crossing = "Cars: ^[] W=" + _carsWaitingUp + " v[] W=" + _carsWaitingDown + "\n"
        + "Peds: " + _pedestriansInCrossing.toString() + " W=" + _pedsWaiting;
    _changed = false;
    return crossing;
  }
}
