package elevenPlus.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import elevenPlus.view.GameUI;

public class Game {
private Map<Integer, Question> questions;
private PlayerList players;
private Board board;
private GameUI ui;
private Player currentPlayer;
private int turnCounter;

	public void gameOption(Integer option) throws IOException{
		if (option == 1){
			turnCounter = 0;
			newGame();
		} else if (option == 2){
			JFrame parent = new JFrame();
			State state = new State();
			final JFileChooser savefile = new JFileChooser();
			savefile.setDialogTitle("Save Your Game");
			savefile.showSaveDialog(parent);
			File savename = savefile.getSelectedFile();
			state.saveState(savename);
		} else if (option == 3){
			State state = new State();
			state.loadState("resources/savegame.bin");
		}
	}
	
	public void newGame() throws IOException{
		QuestionList questionList = new QuestionList();
		questions = questionList.getQuestions("resources/questions.txt");
		board = new Board();
		
		// TEST PLAYERS
		players = new PlayerList();
		players.addPlayer(1,"Will");
		players.addPlayer(2,"John");
		players.addPlayer(3,"James");
		
		Set<Entry<Integer, Question>> qs = questions.entrySet();
		Iterator<Entry<Integer, Question>> i = qs.iterator();
		int x = 0;
		int c = 0;
		while(i.hasNext()){
			Entry<Integer, Question> q = i.next();
			Question t = q.getValue();
			x = x + t.getWrongMove();
			c++;
		}
		
		board.setNumSquares(x);
		int turn = 1;
		players.getPlayer(turn).setTurn(true);
		currentPlayer = turns();
		nextQuestion(currentPlayer);
	}
	
	public Player turns() throws IOException{
		ArrayList<Player> plyrs = players.getAllPlayers();
		for (Player p : plyrs){
			if (p.getTurn() == true){
				currentPlayer = p;
				int id = p.getId();
				if(id == plyrs.size()){
					for (int i = 1; i <= id; i++){
						if (i == 1){
							players.getPlayer(i).setTurn(true);
						} else {
							players.getPlayer(i).setTurn(false);
						}
					}
				} else {
					for (int i = 1; i <= plyrs.size(); i++){
						if (i == id+1){
							players.getPlayer(i).setTurn(true);
						} else if (i == plyrs.size()) {
							turnCounter++;
						} else {
							players.getPlayer(i).setTurn(false);
						} 
					}
				}
				break;
			}
		}
		ui = new GameUI(board, players);
		
		return currentPlayer;
	}
	
	public void nextQuestion(Player player) throws IOException{
		currentPlayer = player;
		System.out.println("Player:"+player.getName());
		ui.updateQuestion(questions.get(turnCounter-1), currentPlayer, questions, turnCounter);
		ui.movePlayer();
	}
	

	public void checkAnswer(ArrayList<String> s, Question q, Player p, PlayerList ps, Board b, Map<Integer, Question> qs, int turnCounter) throws IOException{
		int move = 0;
		this.turnCounter = turnCounter;
		questions = qs;
		board = b;
		players = ps;
		currentPlayer = p;
		System.out.println("The current player is "+currentPlayer.getName());
		List<Integer> ans = q.getAnswer();
		int cnt = ans.size();
		int x = s.size();
		for (int i = 0; i < x; i++){
			for (int z = 0; z < cnt; z++){
				if (Integer.parseInt(s.get(i)) == ans.get(z)-1){
					cnt--;
					System.out.println(cnt);
				}
			}
		}
		if (cnt == 0){
			move = q.getRightMove() + currentPlayer.getPosition();
			players.getPlayer(currentPlayer.getId()).setPosition(move);
			currentPlayer = turns();
			nextQuestion(currentPlayer);
			System.out.println("Right");
		} else {
			move = q.getWrongMove() + currentPlayer.getPosition();
			players.getPlayer(currentPlayer.getId()).setPosition(move);
			currentPlayer = turns();
			nextQuestion(currentPlayer);
			System.out.println("Wrong");
		}
	}
}
