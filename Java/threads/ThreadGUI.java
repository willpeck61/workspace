
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * This is the main GUI class for visualisation the coding/decoding.
 */
 
 public class ThreadGUI extends JFrame {
   
	private static final long serialVersionUID = 7592560396316764020L;
	private JLabel decoder;
    private JButton go;
    private JLabel msg1;
    private JLabel msg2;
   	
	/** The shared accumulator/decoder */
	private Accum sharedResource;
	
	/** Contains first thread with its code and current textual value */	
    private CodeWriter thread1;
	
	/** Contains second thread with its code and current textual value */	
	private CodeWriter thread2;

/**
 * Initialises gui components.
 * @throws Exception 
 */
    
	public ThreadGUI() throws Exception {
    	initComponents();
    }

    
/**
 * Main method instantiates new gui object.
 * @param args
 * @throws InterruptedException
 */
    
    public static void main (String[] args) {
    	try {
    		ThreadGUI gui = new ThreadGUI();
    		gui.setVisible(true);
        	gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	} catch (Exception e){
    		JOptionPane.showMessageDialog(null, "Program Exit - An Input was Cancelled");
    		Runtime.getRuntime().exit(0);
    	}	
    }



/**
 * 
 * This method is called from within the constructor to initialize the form.
 * @throws Exception 
 * @throws InterruptedException 
 */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() throws Exception {
		String src= JOptionPane.showInputDialog("Enter Word 1");
		String dest= JOptionPane.showInputDialog("Enter Word 2");
		
        msg1 = new javax.swing.JLabel();
        decoder = new javax.swing.JLabel();
        msg2 = new javax.swing.JLabel();

        go = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msg1.setText("Message 1");

        decoder.setText("Decoder");

        msg2.setText("Message 2");

	    sharedResource = new Accum(10);
	   
		thread1 = new CodeWriter(src,4000, sharedResource);
		thread2 = new CodeWriter(dest,3500, sharedResource);
		
        thread1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        thread1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        thread1.setName("One"); //Set the name of the thread.
        thread1.setText(src);
        
        thread2.setText(dest);
        thread2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        thread2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        thread2.setName("Two"); // Set the name of the thread.
        
        sharedResource.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        sharedResource.setForeground(new java.awt.Color(255, 0, 51));
        sharedResource.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sharedResource.setToolTipText("This accumulator performs the decoding");
        
        go.setText("Go");
        go.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
				thread1.start();
				thread2.start();
				go.setEnabled(false);                
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(msg1)
                    .addComponent(msg2)
                    .addComponent(decoder))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(thread2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(thread1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(sharedResource, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(go)
                .addGap(264, 264, 264))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msg1)
                    .addComponent(thread1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(decoder)
                    .addComponent(sharedResource, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msg2)
                    .addComponent(thread2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(go)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }
}
