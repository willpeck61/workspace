import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
/**
 * IFrame.java
 *
 * rlc3@le.ac.uk 2016
 * 
 */

public class IFrame extends JFrame implements MouseListener, MouseMotionListener{

    private IMTCanvas canvas;
    private JComboBox box;
    private JCheckBox checkBox;
    private String[] options = {"spiral", "phaseShiftRed", "phaseShiftMix"};
    private JSpinner sp;
    
    public final int size = 60;
    
    public IFrame() {
	
	// Setting for the Frame
	setTitle("Image Manipulation Tool");
	setSize(850,650);
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	Container cp = getContentPane();
	cp.setLayout(null);
	
	// creating and adding the canvas to the frame
	canvas= new IMTCanvas();
	canvas.setBounds(0,0,600,600);
	canvas.addMouseListener(this);
	canvas.addMouseMotionListener(this);
	cp.add(canvas);

	// creating and adding the drop box to the frame
	box = new JComboBox(options);
        box.setBounds(650,50,150,50);
	cp.add(box);

	// creating and adding the check box to the frame
	checkBox= new JCheckBox("active on movement");
	checkBox.setBounds(650,150,170,50);
	cp.add(checkBox);
	    
	sp = new JSpinner();
	sp.setBounds(650,200,40,20);
	cp.add(sp);

    } // IFrame constructor
    
    public static void main(String[] args) {

	IFrame i = new IFrame();
	i.setVisible(true);
    }

    //------------------------mouse listener methods

    public void	mouseClicked(MouseEvent e) {
        int parameter = Integer.parseInt(sp.getValue().toString());
	
	int answer = box.getSelectedIndex();
	switch (answer) {
	case 0 : 
	    ImageManipulation.spiral(canvas.getImage(), parameter,
					   e.getX(),e.getY(), size);

	    canvas.reset();
	    break;
	case 1 : 
	    ImageManipulation.phaseShiftRed(canvas.getImage(), parameter,
					   e.getX(),e.getY(), size);
	    canvas.reset();
	    break;
	case 2 : 
	    ImageManipulation.phaseShiftMix(canvas.getImage(), parameter,
					   e.getX(),e.getY(), size);
	    canvas.reset();
	    break;
	}
	canvas.repaint();
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e) {}
    public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e) {
	if (checkBox.isSelected()) {           
		int parameter = Integer.parseInt( sp.getValue().toString());
		int answer=box.getSelectedIndex();
		switch (answer) {
		case 0 : 
		    BufferedImage temp=canvas.getTempImage();
		    ImageManipulation.spiral(temp, parameter,
					   e.getX(),e.getY(), size);
		    break;

		case 1 : 
		    temp=canvas.getTempImage();
		    ImageManipulation.phaseShiftRed(temp, parameter,
					   e.getX(),e.getY(), size);
		    break;

		case 2 : 
		    temp=canvas.getTempImage();
		    ImageManipulation.phaseShiftMix(temp, parameter,
					   e.getX(),e.getY(), size);
		    break;

		}
		canvas.repaint();
	    }
    }

} // IFrame