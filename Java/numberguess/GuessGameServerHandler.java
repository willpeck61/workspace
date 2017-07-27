package CO2017.exercise3.wrp3.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Guess Game Server Handler
 * @author Will Peck 159029051
 * CO2017 Operating Systems 
 */
public class GuessGameServerHandler extends Thread implements Runnable {
  private final Socket client;
  private final int maxvalue;
  private final long timelimit;
  private final String win          = "WIN:%d%n";
  private final String lose         = "LOSE:%d%n";
  private final String high         = "HIGH:%d:%d%n";
  private final String low          = "LOW:%d:%d%n";
  private final String err          = "ERR:%d%n";
  private final String start        = "START:%d:%d%n";
  
  private Writer output;
  private BufferedReader input;
  private ThreadPoolExecutor thr;
  private String state        = "";
  private Integer guesses     = 0;
  private Long timeremaining  = null;
  
  /**
   * Server handler for multiple clients.
   * @param thr ThreadPoolExecutor
   * @param client Socket
   * @param maxvalue Integer maximum value
   *        allowed for guesses and random
   *        number generation.
   * @param timelimit before game expires.
   * @param name thread name.
   */
  public GuessGameServerHandler(ThreadPoolExecutor thr, 
      Socket client, int maxvalue, long timelimit, String name) {
    this.client = client;
    this.maxvalue = maxvalue;
    this.timelimit = timelimit;
    this.thr = thr;
    this.setName(name);
  }
  
  /**
   * Checks user input and returns error message if incorrect.
   * Called within run method.
   * @param inp user input String
   * @return error - String contains error message
   */
  private String inputError(String inp) {
    String error = null;
    if (null == inp || inp.isEmpty()) {
      error = this.getName() + " ** (ERR non-integer) %ss/%d%n";
    } else if (Integer.parseInt(inp) > maxvalue || Integer.parseInt(inp) < 1) {
      error = this.getName() + " ** (ERR out of range) %ss/%d%n";
    }
    return error; 
  }
  
  /**
   * Server handler and game logic.
   */
  public void run() {
    OutputStream outStream    = null;
    BufferedReader input      = null;
    InputStream inputStream   = null;
    Writer output             = null;
    
    //Create streams and send game start message.
    try {
      inputStream = client.getInputStream();
      input = new BufferedReader(
          new InputStreamReader(inputStream, "UTF-8"));
      outStream = client.getOutputStream();
      output = new OutputStreamWriter(outStream, "UTF-8");
      output.write(String.format(start, maxvalue, timelimit));
      output.flush();
    } catch (UnsupportedEncodingException er) {
      er.printStackTrace();
      System.exit(0);
    } catch (IOException er) {
      return;
    }
    
    //Create a new game state which is added to thread pool.
    GameState gamestate;
    thr.execute(gamestate = new GameState(maxvalue, timelimit, output, this));
    
    //Infinite loop handling input and output to client
    String guess;
    while (true) {
      DecimalFormat deciform = new DecimalFormat("#.#");
      float timeleft = (float) gamestate.getTimeRemaining() / 1000;
      try {
        guess = input.readLine();
        if (null == guess || guess.toLowerCase() == "^c") {
          client.close();
          return;
        } else {
          Formatter outFormat = new Formatter();
          if (!guess.isEmpty()) {
            try {
              gamestate.guess(Integer.parseInt(guess));
              inputError(guess);
            } catch (Exception er) {
              guess = null;
              inputError(guess);
            }
            state         = gamestate.toString();
            guesses       = gamestate.getGuesses();
            timeremaining = gamestate.getTimeRemaining();
            if (null == inputError(guess)) {
              if (state.equals(high)
                  || state.equals(low)) {
                outFormat.format(state, timeremaining, guesses);
              } else if (state.equals(win)
                  || state.equals(lose)) {
                outFormat.format(state, guesses);
                if (state.equals(lose)) {
                  guess = "-";
                }
              }
            } else {
              System.out.printf(String.format(inputError(guess),
                  deciform.format(timeremaining), guesses));
              state = err;
              outFormat.format(state, guesses);
            } 
          } else {
            System.out.printf(String.format(inputError(guess), 
                deciform.format(timeremaining), guesses));
            state = err;
            outFormat.format(state, guesses);
          }
          
          //Write formatted string to out
          output.write(outFormat.toString());
          output.flush();
          timeleft = (float) gamestate.getTimeRemaining() / 1000;
          String[] regex = state.split("\\:");
          if (!state.equals(err) && timeleft > 0) {
            System.out.printf(this.getName() + " " + guess + " (" + regex[0] + ")" 
                + " -" + deciform.format(timeleft) + "/" + guesses + "%n");
          }
          if (state.equals(lose) || state.equals(win)) {
            if (timeleft > 0) {
              System.out.println(this.getName() + " Game over");
            }
          }
          outFormat.close();
        }
      } catch (IOException er) {
        return;
      }
    }
  }
}
