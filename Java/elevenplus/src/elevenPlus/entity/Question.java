package elevenPlus.entity;

import java.util.List;

public class Question {
	private Integer id;
	private String type;
	private String questionText;
	private Integer numOptions;
	private List<String> options;
	private List<Integer> answer;
	private Integer rightMove;
	private Integer wrongMove;
	
	/**
	 * @return the options
	 */
	public List<String> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<String> options) {
		this.options = options;
	}

	/**
	 * @return the answer
	 */
	public List<Integer> getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(List<Integer> answer) {
		this.answer = answer;
	}

	/**
	 * @return the rightMove
	 */
	public Integer getRightMove() {
		return rightMove;
	}

	/**
	 * @param rightMove the rightMove to set
	 */
	public void setRightMove(Integer rightMove) {
		this.rightMove = rightMove;
	}

	/**
	 * @return the wrongMove
	 */
	public Integer getWrongMove() {
		return wrongMove;
	}

	/**
	 * @param wrongMove the wrongMove to set
	 */
	public void setWrongMove(Integer wrongMove) {
		this.wrongMove = wrongMove;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	/**
	 * @return the numOptions
	 */
	public Integer getNumOptions() {
		return numOptions;
	}

	/**
	 * @param numOptions the numOptions to set
	 */
	public void setNumOptions(Integer numOptions) {
		this.numOptions = numOptions;
	}
}
