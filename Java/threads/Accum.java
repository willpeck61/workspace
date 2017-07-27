import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
* The accumulator class can only store 1 alphabetic character, which it can convert to its numerical equivalent.
*/

public class Accum extends JLabel {
	
	private static final long serialVersionUID = 5896569645355586914L;
	private char holding;
	private int delay;
		
/** Argumentless constructor - initialises GUI components */

	public Accum() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		setText("#--->#");
	}

/**
 * Argumented constructor - calls argumentless constructor
 * @param delay time in milliseconds it takes to process a character
 */	
	
	public Accum(int delay){
		this();
		this.delay = delay;
	}
	
/**
 * Receives char from codewriter then stores the char in holding
 * if the char is equal to ASCII equivalent for lower-case alphabet.
 * @param val
 */
	
	public void setAccum(char val){
		this.holding = val;
		if (holding < 97 || holding > 123){
			this.holding = 95;
		}
	}
		
/**
 * Converts the ASCII code stored in holding into the
 * numerical position in the alphabet and returns the new value.
 * @return
 */
	
	public int decode() {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		return this.holding-97;
	}
} // end class Accum
	
