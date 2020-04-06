package org.rythmos.oneconnect.json.request;

import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;

public class AgileMaturityJSONRequest {

	private Long id;
	private String assessmentType;
	private String assessmentDate;
	private String question;
	private String score;
	private ClientDetailBean client;
	private PortfolioBean portfolio;
	private ProjectDetailBean project;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	public String getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(String assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public ClientDetailBean getClient() {
		return client;
	}

	public void setClient(ClientDetailBean client) {
		this.client = client;
	}

	public PortfolioBean getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioBean portfolio) {
		this.portfolio = portfolio;
	}

	public ProjectDetailBean getProject() {
		return project;
	}

	public void setProject(ProjectDetailBean project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "AgileMaturityJSONRequest [id=" + id + ", assessmentType=" + assessmentType + ", assessmentDate="
				+ assessmentDate + ", question=" + question + ", score=" + score + ", client=" + client + ", portfolio="
				+ portfolio + ", project=" + project + "]";
	}

}