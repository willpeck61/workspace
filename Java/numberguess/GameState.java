package CO2017.exercise3.wrp3.server;

import java.io.IOException;
import java.io.Writer;
import java.util.Formatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Guess Game Server
 * @author Will Peck 159029051
 * CO2017 Operating Systems
 */
public class GameState implements Runnable {
  private static final int MINVAL = 1;
  private static Random rand;
  private static int target;
  private static long startTime;
  private static int max;
  private GuessGameServerHandler ggsh;
  private long currentTime;
  private long finishTime;
  private int guesses = 0;
  private boolean finished;
  private int guess;
  private Writer output;
  private final String LOSE = "LOSE:%d%n";
  private final String HIGH = "HIGH:%d:%d%n";
  private final String LOW  = "LOW:%d:%d%n";
  private final String WIN  = "WIN:%d%n";
  
  /**
   * Sets up an instance of the game.
   * Should set the target number for this instance of the game.
   * @param mv the maximum value to be used in the quessing game; 
   *        guesses will be in the range MINVAL..mv
   * @param tl the time limit (in milliseconds) for the game 
   *        (ignore until phase 3)
   * @param out output buffer attached to the client playing the game; 
   *        can be used to directly send messages to the client 
   *        (ignore until phase 3)
   * @param ggsh the handler that is playing this instance 
   *        of the game. (Phase 2 onwards)
   */
  public GameState(int mv, long tl, Writer out, GuessGameServerHandler ggsh) {
    GameState.rand     = new Random();
    GameState.target   = rand.nextInt(mv) + 1;
    GameState.max      = mv;
    this.output = out;
    this.ggsh = ggsh;
    this.finished = false;
    this.guesses  = 0;
    GameState.startTime = TimeUnit.MILLISECONDS.convert(
        System.nanoTime(), TimeUnit.NANOSECONDS);
    this.finishTime = GameState.startTime + tl;
    try {
      out.write(toString());
    } catch (IOException er) {
      er.printStackTrace();
    }
  }
  
  /**
   * When game finishes.
   * @return finished true
   */
  public boolean finished() {
    finished = true;
    return finished;
  }
  
  /**
   * Increases number of guesses by one.
   * @return guesses number of guesses
   */
  public int getGuesses() {
    return guesses;
  }
  
  /**
   * Returns target number.
   * @return target
   */
  public int getTarget() {
    return target;
  }
  
  /**
   * returns time remaining.
   * @return timeleft - time remaining
   */
  public long getTimeRemaining() {
    currentTime = TimeUnit.MILLISECONDS.convert(
        System.nanoTime(), TimeUnit.NANOSECONDS);
    long timeleft = finishTime - currentTime;
    if (timeleft < 0) {
      timeleft = 0;
    }
    return timeleft;
  }
  
  /**
   * Check guess against target.
   * @param gs guess from client
   */
  public void guess(int gs) {
    int tgt = getTarget();
    guess   = gs;
    guesses++;
    if      (gs == tgt) { 
      finished(); 
    } else if (gs > max || gs < 1) {
      --guesses;
    } else if (gs != tgt) {
      getGuesses();
    }
  }
  
  /**
   * Runs methods monitors time remaining.
   * Triggers lose state if time runs out.
   */
  public void run() {
    Formatter outFormat = new Formatter();
    System.out.println(ggsh.getName() + " start watching");
    System.out.println(ggsh.getName() + " target is " + getTarget());
    do {
      float timeleft = (float) getTimeRemaining() / 1000;
      if (getTimeRemaining() == 0.0) {
        try {
          outFormat.format(toString(), guesses);
          output.write(outFormat.toString());
          output.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println(this.ggsh.getName() + " - (LOSE)-" + timeleft);
        System.out.println(this.ggsh.getName() + " Game over");
      };
    } while (getTimeRemaining() != 0);
  }
  
  /**
   * returns the result of last guess as
   * a string.
   * @return out 
   */
  public String toString() {
    String out = null;
    if (getTimeRemaining() <= 0) {
      out = LOSE;
      return out;
    } else {
      if (guesses == 0) {
        return "";
      } else {
        if (guess > target) {
          out = HIGH; 
        } else if (guess < target) {
          out = LOW;
        } else if (guess == target) {
          out = WIN;
        } else if (guess != target) {
          out = LOSE;
        }
        return out;
      }
    }
  }
}
