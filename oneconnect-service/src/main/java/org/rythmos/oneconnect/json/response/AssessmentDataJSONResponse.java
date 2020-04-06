package org.rythmos.oneconnect.json.response;

import org.springframework.stereotype.Component;

@Component
public class AssessmentDataJSONResponse {

	private Long id;

	private String score;

	private String question;

	private String assessmentType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	@Override
	public String toString() {
		return "AssessmentDataJSONRequest [id=" + id + ", score=" + score + ", question=" + question
				+ ", assessmentType=" + assessmentType + "]";
	}

}
