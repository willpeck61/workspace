import java.awt.image.*;
import java.awt.*;
import java.util.*; 
/**
 * 
 * Time-stamp: <2016-01-14 16:56:44 rlc3>
 * 
 * ImageManipulation.java
 *
 * Class allows the manipulation of an image by 
 * two alternative methods.
 *
 * @author Roy Crole
 * 
 *     @param image the image you are performing the twirls on
 *     @param n the smallest distance from the center of square A(x,y) at which pixels are rotated 
 *     @param x the x-coordinate of the centre of the square A(x,y) determined by the mouse 
 *     @param y the y-coordinate of the centre of the square A(x,y) determined by the mouse 
 *     @param size half the length/width of the square
 *
 */

public class ImageManipulation { 

// ---- BEGIN spiral 

    static public void spiral(BufferedImage image, int n, int x, int y, int size) {

    	int imageWidth = image.getWidth(); 
    	int imageHeight= image.getHeight();

    	int imageBoundsE = imageWidth - 10;
    	int imageBoundsS = imageHeight - 10;
    	int imageBoundsNorW = 10;
					 
    	BufferedImage temp=new BufferedImage(imageWidth, imageHeight, image.getType());
    	(temp.getGraphics()).drawImage(image, 0, 0, imageWidth, imageHeight, null);

//---- template code commented out BEGIN 
// check if A(x,y) lies within a 10 pixel boundary of the image
    	if( (x + size <= imageBoundsE && x + size >= imageBoundsNorW) &&
    	    (x - size <= imageBoundsE && x - size >= imageBoundsNorW) && 
    	    (y + size <= imageBoundsS && y + size >= imageBoundsNorW) &&
    		(y - size <= imageBoundsE && y - size >= imageBoundsNorW) ){ 
    		
    		
// loop visiting each pixel in A(x,y) at image position (i,j) 
            for (int i = (x - size); i < (x + size); i++) {
        		for (int j = (y - size); j < (y + size); j++) {
    			
// set d = distance of (x,y) to (i,j)
    				Double d = Math.sqrt(((x - i) * (x - i)) + ((y - j) * (y - j)));
    			
// if d is between n and size then undertake rotation 
    				if (d >= n && d <= size) {
    					
// calculate angle alpha of rotation 
						Double alpha = Math.PI * ((1-(d-n)) / (size-n));
    					
// translate (x,y) to the origin and translate (i,j) relative to (x,y)
// then move to cartesian coordinates (I,J)
    					int I = (-i) - (-x);
    					int J = (-y) - (-j);
    				
// calculate (preI,preJ) from (I,J) 
    					int preI = (int) (I * Math.cos(alpha) + J * Math.sin(alpha));    
    					int preJ = (int) (I * Math.sin(alpha) - J * Math.cos(alpha));
	    				

// move back to cartesian coordinates .... 
    					preI = preI + x;
    					preJ = preJ + y;

// set color of pixel at (i,j) to the color of pixel at (prei,prej)
						image.setRGB(i, j, temp.getRGB(preI, preJ));
    				} else if (d > size) {
    					image.setRGB(i, j, 0xff0000);
    				};           
    			} // end forLoop j
    		} // end forLoop i
    	} // end check that A(x,y) is in image 
    } // end method spiral
// ---- END spiral
    
    
// ---- BEGIN phaseShiftRed
    static public void phaseShiftRed(BufferedImage image, int n, int x, int y, int size) {

    	int imageWidth = image.getWidth(); 
    	int imageHeight= image.getHeight();

	// creates a copy of the image called temp
        BufferedImage temp=new BufferedImage(imageWidth, imageHeight, image.getType());
        (temp.getGraphics()).drawImage(image, 0, 0, imageWidth, imageHeight, null);
				
	// loop through each pixel (i,j) in "A(x,y) intersect (image - boundary)"
        for (int i = x - size; i < (x + size); i++) {
    		for (int j = y - size; j < (y + size); j++) {
		    	// compute colour shift on these pixels where each is less than 2*n away from the edge:
    			if (i > 2 * n && i < imageWidth - 2 * n){
    				if (j > 2 * n && j < imageHeight - 2 * n){
    					
    					// First get the RGB colors from the specified pixels given on the CW1 exercise
    					// then evaluate to red colour component
    					int pixelColour = temp.getRGB(i, j) & 0xff0000; 

    					// then set pixel at (i,j) of image to have this RGB where green and blue = 0 
    	                image.setRGB(i, j, pixelColour);
    				}
    			}
    		} // end loop j 
        } // end loop i 

    } // end phaseShiftRed

// ---- END phaseShiftRed

// ---- BEGIN phaseShiftMix
    
//For phaseShiftMix the new red component will be the red component of the pixel that is n-pixels down. 
//The green component will be 0. The blue component will be the blue component of the pixel that is n-pixels to the left. 
    
    static public void phaseShiftMix(BufferedImage image, int n, int x, int y, int size) {
    	
    	int imageWidth = image.getWidth(); 
    	int imageHeight= image.getHeight();

	// creates a copy of the image called temp
        BufferedImage temp=new BufferedImage(imageWidth, imageHeight, image.getType());
        (temp.getGraphics()).drawImage(image, 0, 0, imageWidth, imageHeight, null);
				
	// loop through each pixel (i,j) in "A(x,y) intersect (image - boundary)"
        for (int i = x - size; i < (x + size); i++) {
    		for (int j = y - size; j < (y + size); j++) { 
    			if (i > 2 * n && i < imageWidth - 2 * n){
    				if (j > 2 * n && j < imageHeight - 2 * n){
    					
    					//Evaluate to the blue element of the nth pixel east
		    			int eastPix =(temp.getRGB(i-n, j) & 0xff);
		    			
		    			//Evaluate to the red element of the nth pixel south 
		    			int southPix = (temp.getRGB(i, j+n) & 0xff0000);
		    			
		    			//Set the colour of the current pixel at (i,j) to blue of nth pixel east and red of nth pixel south
		    			image.setRGB(i, j, 0x0 + eastPix + southPix);
    				}
    			}
    		} // end loop j 
        } // end loop i 
    } // end phaseShiftMix

// ---- END phaseShiftMix

} // ImageManipulation