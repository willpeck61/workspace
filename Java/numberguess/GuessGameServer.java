package CO2017.exercise3.wrp3.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Guess Game Server
 * @author Will Peck 159029051
 * CO2017 Operating Systems
 */
public class GuessGameServer {
  
  private static ThreadPoolExecutor thr;
  private static int clientCounter = 0;
  private static ServerSocket server;
  private static Socket client;
  
  /**
   * Main method to start server.
   * @param args [Port Number] [Maximum Value]
   */
  public static void main(String[] args) {
    if ( args.length != 3 ) {
      System.err.println("Error: Incorrect number of parameters. "
          + "Usage :: java GameGuessServer.java [Port Number] [Maximum Value]"
          + " [Time Limit (secs)]");
      System.exit(0);
    }
    char[] clientRef = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    int port       = Integer.parseInt(args[0]);
    int maxvalue   = Integer.parseInt(args[1]);
    long timelimit = TimeUnit.SECONDS.toMillis(
        Integer.parseInt(args[2])); 
    try {
      server = new ServerSocket(port);
    } catch (IOException er) {
      er.printStackTrace();
      System.exit(0);
    }
    Boolean isStart = true;
    while (true) {
      if (isStart) {
        System.out.println("Starting GuessGame server (" + maxvalue + ", "
            + "" + timelimit + ") on port " + port);
        isStart = false;
      }
      try {
        client  = server.accept();
      } catch (IOException er) {
        System.out.println("Error : " + er);
      }
      thr = (ThreadPoolExecutor) Executors.newCachedThreadPool();
      InetAddress ip = client.getInetAddress();
      System.out.println(clientRef[clientCounter] + " connection : " 
          + ip.toString());
      thr.execute(new GuessGameServerHandler(thr, client, maxvalue, 
          timelimit, "" + clientRef[clientCounter]));
      clientCounter++; 
    } 
  }
}




