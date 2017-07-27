package elevenPlus.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import elevenPlus.entity.Game;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JMenu;

public class Menu {

	private JFrame frmElevenplusWill;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frmElevenplusWill.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmElevenplusWill = new JFrame();
		frmElevenplusWill.setResizable(false);
		frmElevenplusWill.setTitle("ElevenPlus - Will Peck");
		frmElevenplusWill.setBounds(100, 100, 788, 586);
		frmElevenplusWill.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmElevenplusWill.getContentPane().setLayout(null);
		
		JButton btnStartGame = new JButton("Exit");
		btnStartGame.setBounds(0, 515, 780, 29);
		frmElevenplusWill.getContentPane().add(btnStartGame);
		
		textField = new JTextField();
		textField.setBounds(379, 247, 146, 26);
		frmElevenplusWill.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setBounds(251, 250, 69, 20);
		frmElevenplusWill.getContentPane().add(lblPlayer);
		
		JLabel lblInsertName = new JLabel("Insert Name");
		lblInsertName.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblInsertName.setBounds(328, 203, 199, 20);
		frmElevenplusWill.getContentPane().add(lblInsertName);
		
		JLabel lblPlayer_1 = new JLabel("Player 2");
		lblPlayer_1.setBounds(251, 295, 69, 20);
		frmElevenplusWill.getContentPane().add(lblPlayer_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(379, 289, 146, 26);
		frmElevenplusWill.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("New");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Game game = new Game();
				try {
					game.gameOption(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(332, 438, 115, 29);
		frmElevenplusWill.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Load Previous Game");
		btnNewButton_1.setBounds(234, 44, 276, 29);
		frmElevenplusWill.getContentPane().add(btnNewButton_1);
		
		JRadioButton rdbtnPlayer = new JRadioButton("1 Player");
		rdbtnPlayer.setBounds(96, 144, 128, 29);
		frmElevenplusWill.getContentPane().add(rdbtnPlayer);
		
		JRadioButton rdbtnPlayers_2 = new JRadioButton("2 Players");
		rdbtnPlayers_2.setBounds(238, 144, 155, 29);
		frmElevenplusWill.getContentPane().add(rdbtnPlayers_2);
		
		JRadioButton rdbtnPlayers_3 = new JRadioButton("3 Players");
		rdbtnPlayers_3.setBounds(394, 144, 155, 29);
		frmElevenplusWill.getContentPane().add(rdbtnPlayers_3);
		
		JRadioButton rdbtnPlayers_4 = new JRadioButton("4 Players");
		rdbtnPlayers_4.setBounds(556, 144, 155, 29);
		frmElevenplusWill.getContentPane().add(rdbtnPlayers_4);
		
		JLabel lblNumberOfPlayers = new JLabel("Select Number of Players");
		lblNumberOfPlayers.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNumberOfPlayers.setBounds(234, 89, 276, 43);
		frmElevenplusWill.getContentPane().add(lblNumberOfPlayers);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(379, 337, 146, 26);
		frmElevenplusWill.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(379, 385, 146, 26);
		frmElevenplusWill.getContentPane().add(textField_3);
		
		JLabel lblPlayer_2 = new JLabel("Player 3");
		lblPlayer_2.setBounds(251, 340, 69, 20);
		frmElevenplusWill.getContentPane().add(lblPlayer_2);
		
		JLabel lblPlayer_3 = new JLabel("Player 4");
		lblPlayer_3.setBounds(251, 385, 69, 20);
		frmElevenplusWill.getContentPane().add(lblPlayer_3);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBounds(86, 118, 578, 1);
		frmElevenplusWill.getContentPane().add(horizontalGlue);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 41, 31);
		frmElevenplusWill.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Load");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save");
		mnNewMenu.add(mntmNewMenuItem_1);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
