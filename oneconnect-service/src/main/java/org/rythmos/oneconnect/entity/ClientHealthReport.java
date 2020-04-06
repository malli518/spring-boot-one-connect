package org.rythmos.oneconnect.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.rythmos.oneconnect.audit.Auditable;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "project_detail_id", "date", "health_indicator_id" }) }, name = "")
public class ClientHealthReport extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column
	private Date date;
	@ManyToOne
	private HealthIndicatorType healthIndicator;
	@Column
	private float rating;
	@Column
	private String feedback;
	@Column
	private String action;
	@ManyToOne
	private ClientDetail clientDetail;
	@ManyToOne
	private Portfolio portfolio;
	@ManyToOne
	private ProjectDetail projectDetail;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public HealthIndicatorType getHealthIndicator() {
		return healthIndicator;
	}

	public void setHealthIndicator(HealthIndicatorType healthIndicator) {
		this.healthIndicator = healthIndicator;
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

	public ClientDetail getClientDetail() {
		return clientDetail;
	}

	public void setClientDetail(ClientDetail clientDetail) {
		this.clientDetail = clientDetail;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public ProjectDetail getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(ProjectDetail projectDetail) {
		this.projectDetail = projectDetail;
	}

}