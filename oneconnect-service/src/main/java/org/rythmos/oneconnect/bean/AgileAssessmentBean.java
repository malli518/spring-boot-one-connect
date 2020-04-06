package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class AgileAssessmentBean extends AuditBean {

	private Long id;
	private Date assessmentDate;
	private String assessmentType;
	private String question;
	private float score;
	private ClientDetailBean clientDetail;
	private PortfolioBean portfolio;
	private ProjectDetailBean projectDetail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
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

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public ClientDetailBean getClientDetail() {
		return clientDetail;
	}

	public void setClientDetail(ClientDetailBean clientDetail) {
		this.clientDetail = clientDetail;
	}

	public PortfolioBean getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioBean portfolio) {
		this.portfolio = portfolio;
	}

	public ProjectDetailBean getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(ProjectDetailBean projectDetail) {
		this.projectDetail = projectDetail;
	}

	@Override
	public String toString() {
		return "AgileAssessmentBean [id=" + id + ", assessmentDate=" + assessmentDate + ", assessmentType="
				+ assessmentType + ", question=" + question + ", score=" + score + ", clientDetail=" + clientDetail
				+ ", portfolio=" + portfolio + ", projectDetail=" + projectDetail + "]";
	}

}
