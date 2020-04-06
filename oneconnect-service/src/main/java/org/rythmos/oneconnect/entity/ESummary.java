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

@Entity

@Table(name = "ESummary")
public class ESummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long eSummaryId;

	@Column
	private Date eSummaryDate;

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
	private ESummaryAccomplishment eSummaryAccomplishment;

	@ManyToOne
	@JoinColumn
	private ESummaryRiskAndMitigation eSummaryRiskAndMitigation;

	@ManyToOne
	private ESummary eSummarySummary;

	public Long geteSummaryId() {
		return eSummaryId;
	}

	public void seteSummaryId(Long eSummaryId) {
		this.eSummaryId = eSummaryId;
	}

	public Date geteSummaryDate() {
		return eSummaryDate;
	}

	public void seteSummaryDate(Date eSummaryDate) {
		this.eSummaryDate = eSummaryDate;
	}

	public RagStatus getRagStatus() {
		return ragStatus;
	}

	public void setRagStatus(RagStatus ragStatus) {
		this.ragStatus = ragStatus;
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

	public ESummaryAccomplishment geteSummaryAccomplishment() {
		return eSummaryAccomplishment;
	}

	public void seteSummaryAccomplishment(ESummaryAccomplishment eSummaryAccomplishment) {
		this.eSummaryAccomplishment = eSummaryAccomplishment;
	}

	public ESummaryRiskAndMitigation geteSummaryRiskAndMitigation() {
		return eSummaryRiskAndMitigation;
	}

	public void seteSummaryRiskAndMitigation(ESummaryRiskAndMitigation eSummaryRiskAndMitigation) {
		this.eSummaryRiskAndMitigation = eSummaryRiskAndMitigation;
	}

	public ESummary geteSummarySummary() {
		return eSummarySummary;
	}

	public void seteSummarySummary(ESummary eSummarySummary) {
		this.eSummarySummary = eSummarySummary;
	}

}
