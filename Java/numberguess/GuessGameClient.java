package CO2017.exercise3.wrp3.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Guess Game Server
 * @author Will Peck 159029051
 * CO2017 Operating Systems
 */
public class GuessGameClient {
  private static final String win          = "WIN:%d%n";
  private static final String lose         = "LOSE:%d%n";
  private static final String high         = "HIGH:%d:%d%n";
  private static final String low          = "LOW:%d:%d%n";
  private static final String err          = "ERR:%d%n";
  private static final String start        = "START:%d:%d%n";
  private static Socket connect            = null;
  private static BufferedReader input      = null;
  private static BufferedReader userinput  = null;
  private static String nextln             = null;
  private static String guess              = null;
  private static OutputStreamWriter output = null;
  
  /**
   * Allows clients to connect and interact
   * with the running server.
   * @param args requires server name and port.
   * @throws IOException handles IO exceptions.
   */
  public static void main(String[] args) throws IOException {
    if ( args.length != 2 ) {
      System.err.println("Error: Incorrect number of parameters. "
          + "Usage :: java GameGuessClient.java [Server Name] [Port No]");
      System.exit(0);
    }
    
    final String sname = args[0];
    final int port     = Integer.parseInt(args[1]);
    
    try {
      connect = new Socket(sname, port);
      String guess    = "";
      input  = new BufferedReader(
          new InputStreamReader(connect.getInputStream(), "UTF-8"));
      
      OutputStream outputStream = connect.getOutputStream();
      output = new OutputStreamWriter(outputStream, "UTF-8");
      userinput  = new BufferedReader( new InputStreamReader(System.in));
    } catch (IOException er) {
      er.printStackTrace();
    }
    Boolean endgame = false;
    String result = null;
    try {
      nextln = input.readLine();
      do { 
        String[] splitstr = nextln.split("\\:");
        if (splitstr[0].equals("START")) {
          long time = Integer.parseInt(splitstr[2]);
          float timerem = (float) time / 1000;
          System.out.printf("START: range is 1.." + splitstr[1] 
              + " time allowed is " + timerem + "s\n");
          splitstr = null;
        } else {
          splitstr = nextln.split("\\:");
          if (splitstr[0].equals("WIN") || splitstr[0].equals("LOSE")) {
            result = "TURN " + splitstr[1] + ": " + splitstr[0] + "%n";
            endgame = true;
          } else if (splitstr[0].equals("ERR")) {
            result = "TURN " + splitstr[1] + ": " + splitstr[0] + "%n"; 
          } else if (splitstr[0].equals("HIGH") 
              || splitstr[0].equals("LOW")) {
            DecimalFormat deciform = new DecimalFormat("#.#");
            long time = Integer.parseInt(splitstr[1]);
            float timerem = (float) time / 1000;
            result = String.format("TURN %s: %s was %s, %ss remaining%n",
              splitstr[2], 
              guess, splitstr[0], deciform.format(timerem));
            splitstr = null;
          }
          if (endgame) {
            System.out.printf(result);
            System.exit(0);
          } else {
            System.out.printf(result);
          }
        } 
        System.out.printf("Enter guess:");
        
        guess = userinput.readLine();
        output.write(String.format("%s%n", guess));
        output.flush();
        nextln = input.readLine();
      } while (true);
    } catch (UnknownHostException er) {
      er.printStackTrace();
    } catch (IOException er) {
      er.printStackTrace();
    } finally {
      input.close();
      output.close();
      connect.close();
      userinput.close();
      System.exit(0);
    }
  }
}
