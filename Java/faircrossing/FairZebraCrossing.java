package exercise2;

/**
 * Fair version of ZebraCrossing
 * @author Will Peck, 159029051
 */

public class FairZebraCrossing extends ZebraCrossing {
  
  /**
   * Fair zebra crossing makes sure that
   * roughly equal access to the crossing for
   * Persons and Cars by checking that 
   * the numbers waiting is kept equal.
   */
  public FairZebraCrossing(){
    super();
  }
  
  /**
   * Modified start crossing method Person must wait if  
   * more less Persons are waiting that Cars
   */
  public synchronized void startCrossing(Person p) {
    while ((_carsInCrossingUp.size() > 0 || _carsInCrossingDown.size() > 0)
        || (_pedsWaiting < (_carsWaitingUp + _carsWaitingDown))) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    _pedestriansInCrossing.add(p);
    _pedsWaiting--;
    System.out.println(toString());
    notifyAll();
    isChanged();
  }
  
  /**
   * Modified start crossing method Car must wait
   * if more Cars are waiting that Persons
   */
  public synchronized void startCrossing(Car c) {
    if (c.getHeading() == true) {
      while ((_pedestriansInCrossing.size() > 0 || _carsInCrossingUp.size() > 0 )
          && ((_carsWaitingDown + _carsWaitingUp) < _pedsWaiting)) {
        try {
          wait();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    } else if (c.getHeading() == false) {
      while ((_pedestriansInCrossing.size() > 0 || _carsInCrossingDown.size() > 0)
          || ((_carsWaitingDown + _carsWaitingUp) < _pedsWaiting)) {
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
      notifyAll();
      isChanged();
    } else if (_carsInCrossingDown.size() == 0) {
      _carsWaitingDown--;
      _carsInCrossingDown.add(c);
      System.out.println(toString());
      notifyAll();
      isChanged();
    }
  }
}
