package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class AgileAssessmentQuestionBean {

	private long id;

	private String assessmentType;

	private String question;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

}
