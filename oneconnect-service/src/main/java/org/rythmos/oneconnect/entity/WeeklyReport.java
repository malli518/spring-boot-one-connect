package org.rythmos.oneconnect.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.rythmos.oneconnect.audit.Auditable;

@Entity

@Table(name = "WeeklyReport")
public class WeeklyReport extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column
	private Date wReportDate;

	@ManyToOne
	@JoinColumn
	private ClientDetail clientDetail;

	@ManyToOne
	@JoinColumn
	private Portfolio portfolio;

	@ManyToOne
	@JoinColumn
	private ProjectDetail projectDetail;

	@ManyToOne
	@JoinColumn
	private RagStatus ragStatus;

	@ManyToOne
	private WRMajorUpdate wrMajorUpdates;

	@ManyToOne
	private WRRiskAndMitigation wrRiskAndMitigation;

	@ManyToOne
	private WRComment wrComments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getwReportDate() {
		return wReportDate;
	}

	public void setwReportDate(Date wReportDate) {
		this.wReportDate = wReportDate;
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

	public RagStatus getRagStatus() {
		return ragStatus;
	}

	public void setRagStatus(RagStatus ragStatus) {
		this.ragStatus = ragStatus;
	}

	public WRMajorUpdate getWrMajorUpdates() {
		return wrMajorUpdates;
	}

	public void setWrMajorUpdates(WRMajorUpdate wrMajorUpdates) {
		this.wrMajorUpdates = wrMajorUpdates;
	}

	public WRRiskAndMitigation getWrRiskAndMitigation() {
		return wrRiskAndMitigation;
	}

	public void setWrRiskAndMitigation(WRRiskAndMitigation wrRiskAndMitigation) {
		this.wrRiskAndMitigation = wrRiskAndMitigation;
	}

	public WRComment getWrComments() {
		return wrComments;
	}

	public void setWrComments(WRComment wrComments) {
		this.wrComments = wrComments;
	}

}
