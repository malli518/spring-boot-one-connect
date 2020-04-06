package org.rythmos.oneconnect.json.response;

public class AssessmentDataJSONRepsonse {

	private Long id;

	private float score;

	private String assessmentType;

	private String question;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "AssessmentDataJSONRepsonse [id=" + id + ", score=" + score + ", assessmentType=" + assessmentType
				+ ", question=" + question + "]";
	}

}
