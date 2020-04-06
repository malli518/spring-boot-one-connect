package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ClientHealthReportBean extends AuditBean {

	private Long id;
	private float rating;
	private String feedback;
	private String action;
	private Date date;
	private HealthIndicatorTypeBean healthIndicatorType;
	private ClientDetailBean clientDetail;
	private PortfolioBean portfolio;
	private ProjectDetailBean projectDetail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public HealthIndicatorTypeBean getHealthIndicatorType() {
		return healthIndicatorType;
	}

	public void setHealthIndicatorType(HealthIndicatorTypeBean healthIndicatorType) {
		this.healthIndicatorType = healthIndicatorType;
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
		return "ClientHealthReportBean [id=" + id + ", rating=" + rating + ", feedback=" + feedback + ", action="
				+ action + ", date=" + date + ", healthIndicatorType=" + healthIndicatorType + ", clientDetail="
				+ clientDetail + ", portfolio=" + portfolio + ", projectDetail=" + projectDetail + "]";
	}

}
