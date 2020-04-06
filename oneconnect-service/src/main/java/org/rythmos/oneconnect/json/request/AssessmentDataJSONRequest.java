package org.rythmos.oneconnect.json.request;

public class AssessmentDataJSONRequest {

	private String score;

	private String question;

	private String id;

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AssessmentDataJSONRequest [score=" + score + ", question=" + question + ", id=" + id + "]";
	}

}
