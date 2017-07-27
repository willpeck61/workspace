package elevenPlus.entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuestionList {
	
	Map<Integer, Question> questionList;
	Question question;
	
	/**
	 * Creates a Map collection containing the list of questions from the given filename.
	 * @param filename
	 * @return questionList list of questions.
	 * @throws IOException
	 */
	public Map<Integer, Question> getQuestions(String filename) throws IOException{
		int c = 0;
		questionList = new LinkedHashMap<Integer, Question>();
		BufferedReader txtline = new BufferedReader(new FileReader(filename));
		String l = txtline.readLine();
		while(l != null){
			List<String> p = new ArrayList<String>();
			question = new Question();
			if (l.equals("Single Choice") || l.equals("Multiple Choice")){
				question.setId(c);
				question.setQuestionText(txtline.readLine());
				question.setNumOptions(Integer.parseInt(txtline.readLine()));
				for (int i = 0; i < question.getNumOptions(); i++){
					p.add(i, txtline.readLine());
				}
				question.setOptions(p);
				StringBuffer answers = new StringBuffer(txtline.readLine());
				List<Integer> f = new ArrayList<Integer>();
				for (int x = 0; x < answers.length(); x++){
					if (answers.charAt(x) != 44){
						String a = String.valueOf(answers.charAt(x));
						f.add(Integer.parseInt(a));
					}
				}
				question.setAnswer(f);
				question.setRightMove(Integer.parseInt(txtline.readLine()));
				question.setWrongMove(Integer.parseInt(txtline.readLine()));
				questionList.put(c, question);
				c++;
			}
			question = null;
			l = txtline.readLine();
		}
		txtline.close();
		return questionList;
	}
	
}
