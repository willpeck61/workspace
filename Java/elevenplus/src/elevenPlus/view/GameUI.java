package elevenPlus.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import elevenPlus.entity.Board;
import elevenPlus.entity.Game;
import elevenPlus.entity.Player;
import elevenPlus.entity.PlayerList;
import elevenPlus.entity.Question;
import elevenPlus.entity.QuestionList;
import elevenPlus.entity.State;

import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;

public class GameUI {
	private Board board;
	private ArrayList<Player> players;
	private Game game;
	private JFrame frame;
	private JPanel gamePanel;
	private JPanel questPanel;
	private JPanel answerPanel;
	private Integer numPlayers;
	private Player plyr1;
	private Player plyr2;
	private Player plyr3;
	private Player plyr4;
	private ArrayList<Integer> p1board;
	private ArrayList<Integer> p2board;
	private ArrayList<Integer> p3board;
	private ArrayList<Integer> p4board;
	private String questionText;
	private JLabel lblQuestiontext;
	private Integer options;
	private JCheckBox chckbxAnswertext;
	private List<String> answerText;
	private ButtonGroup ansGroup;
	private Question question;
	private PlayerList ps;
	private Player currentPlayer;
	private ArrayList<Box> boxes;
	private ArrayList<String> multiAns;
	private JLabel lblCurrentGame;
	private Map<Integer, Question> questions;
	private int turnCounter;
	

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public GameUI(Board board, PlayerList pllist) {
		this.board = board;
		ps = pllist;
		this.players = ps.getAllPlayers();

		int x = 0;
		for (Player p : players){
			numPlayers = x;
			if (p.getId() == 1){
				plyr1 = p;
			} else if (p.getId() == 2){
				plyr2 = p;
			} else if (p.getId() == 3){
				plyr3 = p;
			} else if (p.getId() == 4){
				plyr4 = p;
			}
			x++;
		}

		setGameBoard();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void movePlayer() {
		JLabel lblAnswer = new JLabel("Answer");
		lblAnswer.setBounds(134, 0, 69, 20);
		answerPanel.add(lblAnswer);
		game = new Game();
		ArrayList<String> answers = new ArrayList<String>();
		JButton btnCheckAnswer = new JButton("Check Answer");
		btnCheckAnswer.setBounds(187, 158, 155, 29);
		answerPanel.add(btnCheckAnswer);
		btnCheckAnswer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ansGroup != null){
					answers.add(ansGroup.getSelection().getActionCommand());
				} else {
					for (String m : multiAns){
						answers.add(m);
					}
				}
				try {
					frame.dispose();
					game.checkAnswer(answers, question, currentPlayer, ps, board, questions, turnCounter);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void updateQuestion(Question q, Player currentPlayer, Map<Integer, Question> questions2, int turnCounter){
		this.currentPlayer = currentPlayer;
		this.turnCounter = turnCounter;
		questions = questions2;
		options = q.getNumOptions();
		questionText = "<html><p>"+q.getQuestionText()+"</p></html>";
		lblQuestiontext = (JLabel) questPanel.getComponentAt(15,28);
		System.out.println(q.getId()+" "+q.getQuestionText());
		questPanel.remove(lblQuestiontext);
		lblQuestiontext.setText(questionText);
		lblQuestiontext.setBounds(15, 28, 331, 143);
		questPanel.add(lblQuestiontext);
		
		gamePanel.remove(lblCurrentGame);
		lblCurrentGame.setText("<html><h1>It's " +currentPlayer.getName()+ "'s Turn</h1></html>");
		lblCurrentGame.setBounds(300, 16, 200, 20);
		gamePanel.add(lblCurrentGame);
		
		answerText = q.getOptions();
		int b = 0;
		ansGroup = null;
		if (q.getAnswer().size() == 1){
			ansGroup = new ButtonGroup();
		} else {
			multiAns = new ArrayList<String>();
		}
		for (int i = 0; i < options; i++){
			chckbxAnswertext = new JCheckBox(answerText.get(i));
			chckbxAnswertext.setActionCommand(""+i+"");
			if (q.getAnswer().size() == 1){
				ansGroup.add(chckbxAnswertext);
			} else {
				multiAns.add(chckbxAnswertext.getActionCommand());
			}
			chckbxAnswertext.setBounds(11, 32+b, 139, 29);
			answerPanel.add(chckbxAnswertext);
			b = b + 25;
		}
		
		question = q;
	}
	
	public void setGameBoard(){
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(15, 309, 748, 219);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		gamePanel = new JPanel();
		gamePanel.setBounds(15, 16, 748, 284);
		frame.getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
		int s = board.getNumSquares();

		int b1 = 0;
		boxes = new ArrayList<Box>();
		p1board = new ArrayList<Integer>();
		p2board = new ArrayList<Integer>();
		p3board = new ArrayList<Integer>();
		p4board = new ArrayList<Integer>();
		for (int i = 0; i < s; i++){
			Box verticalBox = Box.createVerticalBox();
			if (players.size() >= 1){
				verticalBox = Box.createVerticalBox();
				verticalBox.setBorder(new LineBorder(new Color(0, 0, 0)));
				verticalBox.setBackground(Color.RED);
				verticalBox.setBounds(99+b1, 59, 30, 30);
				boxes.add(verticalBox);
			}
			if (players.size() >= 2){
				verticalBox = Box.createVerticalBox();
				verticalBox.setBorder(new LineBorder(new Color(0, 0, 0)));
				verticalBox.setBackground(Color.RED);
				verticalBox.setBounds(99+b1, 115, 30, 30);
				boxes.add(verticalBox);
			}
			if (players.size() >= 3){
				verticalBox = Box.createVerticalBox();
				verticalBox.setBorder(new LineBorder(new Color(0, 0, 0)));
				verticalBox.setBackground(Color.RED);
				verticalBox.setBounds(99+b1, 171, 30, 30);
				boxes.add(verticalBox);
			}
			if (players.size() >= 4){
				verticalBox.setBorder(new LineBorder(new Color(0, 0, 0)));
				verticalBox.setBackground(Color.RED);
				verticalBox.setBounds(99+b1, 228, 30, 30);
				boxes.add(verticalBox);
			}
			b1 = b1 + 32;
		}
		
		int z = 0;
		int t = 0;
		for (Box b11 : boxes){
			if (t == 0 && t <= players.size()){
				p1board.add(z);
			} else
			if (t == 1 && t <= players.size()){
				p2board.add(z);
			} else
			if (t == 2 && t <= players.size()){
				p3board.add(z);
			} else
			if (t == 3 && t <= players.size()){
				p4board.add(z);
			}
			t++;
			if (t == players.size()){
				t = 0;
			}
			b11.setOpaque(true);
			
			gamePanel.add(b11);
			z++;
		}
		
		if (plyr1 != null){
			try {
				boxes.get(p1board.get(plyr1.getPosition())).setBackground(Color.GREEN);
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(frame, "PLAYER 1 WINS!");
				frame.dispose();
			}
		};
		if (plyr2 != null){
			try {
				boxes.get(p2board.get(plyr2.getPosition())).setBackground(Color.GREEN);
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(frame, "PLAYER 2 WINS!");
				frame.dispose();
			}
		};		
		if (plyr3 != null){
			try {
				boxes.get(p3board.get(plyr3.getPosition())).setBackground(Color.GREEN);
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(frame, "PLAYER 3 WINS!");
				frame.dispose();
			}
		}
		if (plyr4 != null){
			try {
				boxes.get(p4board.get(plyr4.getPosition())).setBackground(Color.GREEN);
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(frame, "PLAYER 4 WINS!");
				frame.dispose();
			}
		};

		questPanel = new JPanel();
		questPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		questPanel.setBounds(15, 16, 361, 187);
		panel.add(questPanel);
		questPanel.setLayout(null);
		
		JLabel lblQuestion = new JLabel("Question");
		lblQuestion.setBounds(138, 0, 69, 20);
		questPanel.add(lblQuestion);
		
		lblQuestiontext = new JLabel(questionText);
		lblQuestiontext.setBounds(15, 28, 331, 143);
		questPanel.add(lblQuestiontext);
		
		answerPanel = new JPanel();
		answerPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		answerPanel.setBounds(391, 16, 342, 187);
		panel.add(answerPanel);
		answerPanel.setLayout(null);
		
		if (numPlayers >= 3){
			JLabel lblNewLabel = new JLabel(plyr4.getName());
			lblNewLabel.setBounds(15, 11, 69, 20);
			gamePanel.add(lblNewLabel);
		} 
		if (numPlayers >= 2){
			JLabel lblNewLabel = new JLabel(plyr1.getName());
			lblNewLabel.setBounds(15, 68, 69, 20);
			gamePanel.add(lblNewLabel);
		} 
		if (numPlayers >= 1){
			JLabel lblPlayer_1 = new JLabel(plyr2.getName());
			lblPlayer_1.setBounds(15, 125, 69, 20);
			gamePanel.add(lblPlayer_1);
		} 
		if (numPlayers >= 0){
			JLabel lblPlayer = new JLabel(plyr3.getName());
			lblPlayer.setBounds(15, 182, 69, 20);
			gamePanel.add(lblPlayer);
		}
		
		lblCurrentGame = new JLabel("Current Game");
		lblCurrentGame.setBounds(318, 16, 99, 20);
		gamePanel.add(lblCurrentGame);
		
		Button button = new Button("Save and Exit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game = new Game();
				try {
					game.gameOption(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		button.setActionCommand("");
		button.setBounds(568, 10, 170, 27);
		gamePanel.add(button);
		frame.setVisible(true);

	}
}
