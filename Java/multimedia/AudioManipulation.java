/**
* AudioManipulation.java
*
* Time-stamp: <2016-02-12 16:39:46 rlc3>
*
* Defines mixer/effect functions on audio streams
* Utilises the AudioInputStream class 
* 
* To compile: javac -classpath editor.jar:. RunEffects.java
* To run use: java -classpath editor.jar:. RunEffects
* 
*/

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class AudioManipulation {

/**** echo *****************************************************************/

   public static AudioInputStream echo(AudioInputStream ais, int timeDelay, double fading0, double fading1) {

	byte[] a = null;
	int[] data, ch0, ch1;
	int max;

	try{
	    // AudioInputStream methods 
	    // TRY PRINTING OUT SOME VALUES ..... 
	    int numChannels     = ais.getFormat().getChannels();
	    int sampleSize 	= ais.getFormat().getSampleSizeInBits();
	    boolean isBigEndian = ais.getFormat().isBigEndian();
	    float sampleRate 	= ais.getFormat().getSampleRate();
	    float frameRate 	= ais.getFormat().getFrameRate();
        int frameSize 	= ais.getFormat().getFrameSize();
	    int frameLength 	= (int) ais.getFrameLength();

        // sampleRate = framerate = 44100.0 Hz (also playback rate = sampling rate!) 
	    // calculate delay in frames 
	    int frameDelay = (int) (timeDelay/1000.0 * frameRate);

	    // reset the AudioInputStream (mark goes to the start) 
	    ais.reset();

	    // create a byte array of the right size to store the ais 
	    a = new byte[(int) frameLength*frameSize];

	    // fill the byte array with the data of the AudioInputStream
	    ais.read(a);

	    // Create an integer array, data, of the right size
	    // only reason to do this is enabling type double mixing calculations  
	    data = new int[a.length/2];

	    // fill the integer array by combining two bytes of the
	    // byte array a into one integer
	    // Bytes HB and LB Big Endian make up one integer 
	    for (int i=0; i<data.length; ++i) {
	    	
			/* First byte is HB (most significant digits) - coerce to 32-bit int */
			// HB =def sign_extend(a[2*i]) from 8 bit byte to 32 bit int 
			int HB = (int) a[2*i];
			
			/* Second byte is LB (least significant digits) - coerce to 32-bit int */
			// LB =def sign_extend(a[2*i+1]) from 8 bit byte to 32 bit int 
			int LB = (int) a[2*i+1];
			
			// note that data[i] =def sign_extend(HB.LB) 
			// | : Bool^32 x Bool^32 -----> Bool^32 where Bool = {0, 1} 
			data[i] =  HB << 8 | (LB & 0xff); 
	    }

	    // split samples into two channels
	    // if both channels are faded by the same factor 
	    // then there is no need to split the channels 
	    ch0 = new int[data.length/2];
	    ch1 = new int[data.length/2];
	    for (int i=0; i<data.length/2; i++) {
			ch0[i] = data[2*i];
			ch1[i] = data[2*i+1];
	    }

	    // Adding a faded copy of the early signal to the later signal
	    // THIS IS THE ECHO !!
	    for (int i=frameDelay; i<ch0.length; ++i) {
			ch0[i] += (int) (ch0[i-frameDelay]*fading0);
			ch1[i] += (int) (ch1[i-frameDelay]*fading1);
	    }

	    // combine the two channels
	    for (int i=0; i<data.length; i+=2) {
			data[i]   = ch0[i/2];
			data[i+1] = ch1[i/2];
	    }  

	    // get the maximum amplitute
	    max=0;
	    for (int i=0; i<data.length; ++i) {
	    	max=Math.max(max,Math.abs(data[i]));
	    }

           // 16 digit 2s-complement range from -2^15 to +2^15-1 = 256*128-1
	    // therefore we linearly scale data[i] values to lie within this range .. 
	    // .. so that each data[i] has a 16 digit "HB.LB binary representation" 
	    if (max > 256*128-1) {
	        System.out.println("Sound values are linearly scaled by " + (256.0*128.0-1)/max + 
	            " because maximum amplitude is larger than upper boundary of allowed value range."); 
			for (int i=0; i<data.length; ++i) {
			    data[i] = (int) (data[i]*(256.0*128.0-1)/max);
			}
        }

	    // convert the integer array to a byte array 
	    for (int i=0; i<a.length; i+=2) {
			a[i] = (byte) ((data[i / 2] >> 8) & 0xff);
			a[i+1] = (byte) (data[i/2] & 0xff);
	    }

	} catch(Exception e){
	    System.out.println("Something went wrong");
	    e.printStackTrace();
	}

	// create a new AudioInputStream out of the the byteArray
	// and return it.
	return new AudioInputStream(new ByteArrayInputStream(a),
				    ais.getFormat(),ais.getFrameLength());
   }


/**** scaleSHD *****************************************************************/

   public static AudioInputStream scaleSHD(AudioInputStream ais) {

		byte[] a = null;
		int[] data, ch0, ch1;
		int max;

		try{
		    // AudioInputStream methods 
		    // TRY PRINTING OUT SOME VALUES ..... 
		    int numChannels     = ais.getFormat().getChannels();
		    int sampleSize 	    = ais.getFormat().getSampleSizeInBits();
		    boolean isBigEndian = ais.getFormat().isBigEndian();
		    float sampleRate 	= ais.getFormat().getSampleRate();
		    float frameRate 	= ais.getFormat().getFrameRate();
	        int frameSize 	    = ais.getFormat().getFrameSize();
		    int frameLength 	= (int) ais.getFrameLength();
		    
		    System.out.println("Number of Channels: " + numChannels);
		    System.out.println("Sample Size: " + sampleSize);
		    System.out.println("Big Endian: " + isBigEndian);
		    System.out.println("Sample Rate: " + sampleRate);
		    System.out.println("Frame Rate: " + frameRate);
		    System.out.println("Frame Size: " + frameSize);
		    System.out.println("Frame Length: " + frameLength);

	        // sampleRate = framerate = 44100.0 Hz (also playback rate = sampling rate!) 
		    // calculate delay in frames 
		    // int frameDelay = (int) (timeDelay/1000.0 * frameRate);

		    // reset the AudioInputStream (mark goes to the start) 
		    ais.reset();

		    // create a byte array of the right size to store the ais 
		    a = new byte[(int) frameLength * frameSize];

		    // fill the byte array with the data of the AudioInputStream
		    ais.read(a);

		    // Create an integer array, data, of the right size
		    // only reason to do this is enabling type double mixing calculations  
		    data = new int[a.length/2];

		    // fill the integer array by combining two bytes of the
		    // byte array a into one integer
		    // Bytes HB and LB Big Endian make up one integer 
		    for (int i = 0; i < data.length; ++i) {
		    	
				/* First byte is HB (most significant digits) - coerce to 32-bit int */
				// HB =def sign_extend(a[2*i]) from 8 bit byte to 32 bit int 
				int HB = (int) a[2 * i];
				
				/* Second byte is LB (least significant digits) - coerce to 32-bit int */
				// LB =def sign_extend(a[2*i+1]) from 8 bit byte to 32 bit int 
				int LB = (int) a[2 * i + 1];
				
				// note that data[i] =def sign_extend(HB.LB) 
				// | : Bool^32 x Bool^32 -----> Bool^32 where Bool = {0, 1} 
				data[i] =  HB << 8 | (LB & 0xff); 
		    }

		    /**
		     * Start at middle point (data.length / 2)
		     * Get the percentage through sample ((i - middle point) / (middle point))
		     * Scale linear 9 * percentage through sample + 1  
		     */
		    double scale = 0;
		    for (int i = data.length / 2; i < data.length; i++) {
	    		scale = 9.0 * ((i - data.length / 2.0) / (data.length / 2.0)) + 1;
	    		data[i] = (int) (data[i] * scale);
	    		System.out.println(scale);
		    }

		    // get the maximum amplitute
		    max=0;
		    for (int i = 0; i < data.length; ++i) {
		    	max=Math.max(max,Math.abs(data[i]));
		    }

		    // convert the integer array to a byte array 
		    for (int i = 0; i<a.length; i+=2) {
				a[i] = (byte) ((data[i / 2] >> 8) & 0xff);
				a[i + 1] = (byte) (data[i / 2] & 0xff);
		    }

		} catch(Exception e){
		    System.out.println("Something went wrong");
		    e.printStackTrace();
		}

	// create a new AudioInputStream out of the (new) byte array a
	// and return it.
	return new AudioInputStream(new ByteArrayInputStream(a),
				    ais.getFormat(),ais.getFrameLength());
   }


/**** delChan *****************************************************************/

   public static AudioInputStream delayChan(AudioInputStream ais, int delayInMilliseconds){

	byte[] a=null, b=null;

	try {

		float frameRate   = ais.getFormat().getFrameRate();
		int frameSize 	  = ais.getFormat().getFrameSize();
		int frameLength   = (int) ais.getFrameLength();
		int delayInFrames = (int) (delayInMilliseconds / 1000.0 * frameRate);
		int delayInBytes  = delayInFrames * frameSize;

		ais.reset();

		// create a byte array of the right size to store the input AudioInputStream
		a = new byte[(int) frameLength * frameSize];
		
		// fill the byte array with the data of the AudioInputStream
		ais.read(a);

		// declare the new byte array b 
		b = new byte[a.length + delayInBytes];
	
        // HINT: walk along the array a and place each byte of a into the
		// correct position in array b
		for (int i = 0; i < a.length; i = i + 4){
			b[i + delayInBytes] = a[i];
			b[i + 1 + delayInBytes] = a[i + 1];
			b[i + 2] = a[i + 2];
			b[i + 3] = a[i + 3];	
		}

	} catch(Exception e){
	    System.out.println("Something went wrong");
	    e.printStackTrace();
	}
	
	return new AudioInputStream(new ByteArrayInputStream(b),
				    ais.getFormat(), b.length / ais.getFormat().getFrameSize());
   } // end delChan


/**** addNote *****************************************************************/

   public static AudioInputStream addNote(AudioInputStream ais, 
		double frequency, double frequency1, int noteLengthInMilliseconds) {
		byte[] a = null;
		int[] data = null; 
     
		try { 
			float frameRate = ais.getFormat().getFrameRate();
			int numChannels = ais.getFormat().getChannels(); 
			int frameSize 	= ais.getFormat().getFrameSize(); 
			
			// note length in frames, bytes and ints 

			/* ----- template code commented out BEGIN */
			
	   		int noteLengthInFrames = (int) (noteLengthInMilliseconds/1000.0 * frameRate);
	   		int noteLengthInBytes  =  noteLengthInFrames * frameSize; 
	   		int noteLengthInInts   =  noteLengthInBytes / 2;
	
       		// byte array a for output
			a = new byte[noteLengthInBytes];
			
			// int array data to hold the pure sounds frequency0 and frequency1 
			data = new int[noteLengthInInts];
				

			// create the note as a data array of samples 
			// each sample value data[i] is calculated using 
			// the time t at which data[i] is played
			double amp = 64 * 256; //Check these
			double timeA = 0.0;
			double timeB = 0.0;
			for (int i = 0; i < noteLengthInInts; i = i + 2){
				timeA = i / frameRate;
				timeB = (i + 1) / frameRate;
				data[i] = (int) (amp * Math.sin(frequency * 2 * Math.PI * timeA));
				data[i + 1] = (int) (amp * Math.sin(frequency1 * 2 * Math.PI * timeB));
			}   
			
			// update the byte array to the new values from the data array  
			for (int i = 0; i < a.length; i += 2) {
				a[i] = (byte) ((data[i / 2] >> 8) & 0xff);
				a[i + 1] = (byte) (data[i / 2] & 0xff);
		    }

		} catch(Exception e){
			System.out.println("Something went wrong");
			e.printStackTrace();
		}

		return append(new AudioInputStream(new ByteArrayInputStream(a), 
				ais.getFormat(), a.length / ais.getFormat().getFrameSize()), ais);
   }  // end addNote


/**** append THIS METHOD IS SUPPLIED FOR YOU ********************************************/

   public static AudioInputStream append(AudioInputStream ais1, AudioInputStream ais2){
		
		byte[] a,b,c = null;
		try {
			a=new byte[(int) ais1.getFrameLength() * ais1.getFormat().getFrameSize()];
			ais1.read(a);
			b=new byte[(int) ais2.getFrameLength() * ais2.getFormat().getFrameSize()];
			ais2.read(b);
			c=new byte[a.length + b.length];
			for (int i=0; i<c.length; i++) {
				if (i<a.length) {
					c[i]=a[i];
				}
				else	
					c[i]=b[i-a.length];
			}
		} catch(Exception e){
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
			

       return new AudioInputStream(new ByteArrayInputStream(c),
				    ais1.getFormat(), c.length/ais1.getFormat().getFrameSize());
	} // end append

/**** tune  *****************************************************************/

	public static AudioInputStream tune(AudioInputStream ais){
		// create an empty AudioInputStream (of frame size 0) 
		byte[] c = new byte[1];
               AudioInputStream temp = new AudioInputStream(new ByteArrayInputStream(c),ais.getFormat(),0);

		// Here are the notes
		double C	= 261.63;
		double D	= 293.66; 	
		double E	= 329.63; 	
		double F	= 349.23; 	
		double G	= 392.00; 	
		double A	= 440.00; 	
		double B	= 493.88; 
		 
		// and the lengths in milliseconds 
		int s = 500;
		int l = 1000;
		
		// The notes of Twinkle Twinkle Little Star - use a 2D int array 
		// a row for each note ; each note has a frequency and length (2 columns)  
		// WARNING: Don't confuse columns 0 and 1 with the channels !!*/
	    double[][] notes = new double [][]
	      {{C,s}, {C,s}, {G,s}, {G,s}, {A,s}, {A,s}, {G,l}, 
		   {F,s}, {F,s}, {E,s}, {E,s}, {D,s}, {D,s}, {C,l}, 
		   {G,s}, {G,s}, {F,s}, {F,s}, {E,s}, {E,s}, {D,l}, 
		   {G,s}, {G,s}, {F,s}, {F,s}, {E,s}, {E,s}, {D,l},
		   {C,s}, {C,s}, {G,s}, {G,s}, {A,s}, {A,s}, {G,l},
		   {F,s}, {F,s}, {E,s}, {E,s}, {D,s}, {D,s}, {C,l}}
	    ;
	   
		// use a loop to add the notes and spaces of the tune, one by one, 
		// to temp 
	    int count = 0;
	    for (int i = 41; i >= 0; --i){
	    	count++;
	    	if (count <= 7){
	    		if (notes[i][1] == 500){
	    			temp = addNote(temp, 0, 0, 100);
	    			temp = addNote(temp, 0, notes[i][0], (int) notes[i][1]);
	    		} else {
	    			temp = addNote(temp, 0, 0, 400);
	    			temp = addNote(temp, 0, notes[i][0], (int) notes[i][1]);
	    		}
	    	} 
	    	else if (count > 7){
	    		if (notes[i][1] == 500){
	    			temp = addNote(temp, 0, 0, 100);
	    			temp = addNote(temp, notes[i][0], 0, (int) notes[i][1]);
	    		} else {
	    			temp = addNote(temp, 0, 0, 400);
	    			temp = addNote(temp, notes[i][0], 0, (int) notes[i][1]);
	    		}
	    		if (count == 14) {
	    			count = 0;
	    		}
	    	}
	    }
        return append(temp,ais);
   }

   /* ---- 

      Why is the middle phrase of delayChan applied to tune a "perfect stereo phrase"  
      (for a suitable delay which you should state)?
      
        s = short note = 500  l = long note = 1000  
        ((s + short delay) * (qty of s) ) + (l + long delay) == delay 
        ((500 + 100) * 6) + (1000 + 400) == 5000
        
       * DelayChan using this delay will align the offset channels
       * for the middle phrase.  When the total length of delays and note
       * note lengths are added together, this means
       * each are played simultaneously, one on ch0 and the other ch1. 

    ---- */


} // AudioManipulation


