package elevenPlus.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class State {
	QuestionList questions;
	PlayerList players;
	Map<Integer, Question> saveQuestions;
	ArrayList<Player> savePlayers;
	
	public void saveState(File filename){
		saveQuestions = questions.questionList;
		savePlayers = players.playerList;
	}
	
	public LinkedHashMap<String, Object> loadState(String filename){
		
		
		
		return null;
	}
	
}
