
import java.awt.Color;
import java.lang.Runnable;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

 /**
  * CodeWriter holds the current state of a piece of text that is being decoded.
  */
 
public class CodeWriter extends JLabel implements Runnable  {
 
	private static final long serialVersionUID = -2559577628903147922L;
	private StringBuffer buf;
	private int delay;
	private Accum ref;
	private Thread thread;
	private int threadNo;
	private int id;
	private int strlen;
	
/**
 * Start a thread.
 * Set the thread number by comparing setName value.
 */
	
	public void start() {
		String curThr = this.getName();
		thread = new Thread(this);
		thread.setName(curThr);
		if (curThr == "One"){
			threadNo = 0;
		} else {
			threadNo = 1;
		}
	    thread.start();
	}
	
/**
 * Run method on threads 
 */
	
	public void run() {
		char curChar;
		int returnVal = 0;
		
		this.setText(buf.toString());
		
		/**
		 * Loop around the StringBuffer and pass each char to Accum
		 * to decode();
		 */
		
		while (thread != null){
		for (int i = 0; i < strlen; i++){
			
				try {
					/**
					 * Report Thread Leaks
					 */
					if (threadMonitor() == false){
						System.err.println("\nINFO: Not Synchronized on thread " +Thread.currentThread().getName()+"\n");
					} else {
						System.out.println("INFO: Current thread - " +Thread.currentThread().getName()+"\n");
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				/**
				 * Synchronize calls to the ref 
				 * instance of accum (locked)
				 * ie. only one thread can access this
				 * resource at a time 
				 */
				
				synchronized(ref){
					curChar = Character.toLowerCase(buf.charAt(i));
				
				/**
				 * Pass current char to setAccum method in Accum class.
				 * Then get return value of the decode method 
				 * from Accum class.
				 */
				
					ref.setAccum(curChar);
					returnVal = ref.decode() + 1;
				
				/**
				 * Delete char in StringBuffer at the current
				 * position in sequence.
				 * Then insert the return value from Accum.decode()
				 * into the StringBuffer.
				 * Then update JLabel text.
				 */
				
					buf.deleteCharAt( i );
					buf.insert(i, returnVal);
					this.setText(buf.toString());
				}
				
				/**
				 * If the decode() returned value contains more than
				 * two chars adjust the strlen to compensate.
				 */
				
				if(returnVal > 9 || returnVal == -1){
					if(buf.length() > strlen){
						strlen = buf.length();
						if(i < strlen){
							i++;
						}
					}
				
				}
				/**
				 * Set the text in the "decoder" box with the 
				 * current StringBuffer char and the replacement
				 * return character from Accum.decode(). 
				 */
				
				this.ref.setText(Character.toString(curChar) + "--->" + returnVal);
				
				/**
				 * Set ID to match the current thread number.
				 */
				
				try {
					if (this.getName() == "One"){
						id = 0;
					} else {
						id = 1;
					}
					threadMonitor();
				} catch (InterruptedException e){
					
				}
				
				/**
				 * Pause the thread for specified time.
				 */
				
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		stop();
		}
	}

/**
 * Stop the current thread. 
 */
	
	public void stop() {
		thread = null;
		this.setOpaque(true);
		this.setBackground(Color.GREEN);
		JOptionPane.showMessageDialog(null, "Thread " +this.getName()+ " Finished");
	}
	
/**
 * Checks for leaks between threads and returns false if leak is found.
 * @return
 * @throws InterruptedException
 */
	
	public synchronized boolean threadMonitor() throws InterruptedException {
		boolean thisthread = false;
		if (id == threadNo){
			thisthread = true;
		}
		return thisthread;
	}
	
/** 
 * Creates a code to be decoded.
 * @param nval initial text to be decoded.
 * @param ndelay Delay in milliseconds before next decode request
 * @param t reference to the accumulator to pass characters to
 * @throws Exception 
 */
	
	public CodeWriter(String nval, int ndelay, Accum t) throws Exception {
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		delay = ndelay;
		buf = new StringBuffer();
		ref = t;
		for (int x = 0;x < nval.length(); x++){
				buf.insert(x, nval.charAt(x));
		}
		strlen = nval.length();
	}

} // end class ArrayWriter