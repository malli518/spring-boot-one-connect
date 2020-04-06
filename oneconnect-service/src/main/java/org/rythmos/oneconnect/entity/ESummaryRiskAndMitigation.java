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
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "ESummaryRiskAndMitigation", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "mitigationDesc", "riskDesc", "e_summary_e_summary_id" }) })
public class ESummaryRiskAndMitigation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "riskAndMitigationId", updatable = false, nullable = false)
	private Long riskAndMitigationId;

	@Column(length = 380)
	private String riskDesc;

	@Column(length = 380)
	private String mitigationDesc;

	@Column
	private Long riskAge;

	@Column
	private String status;

	@Column
	private Date createdDate;

	@Column
	private Date closedDate;

	@ManyToOne
	@JoinColumn
	private ESummary eSummary;

	public Long getRiskAndMitigationId() {
		return riskAndMitigationId;
	}

	public void setRiskAndMitigationId(Long riskAndMitigationId) {
		this.riskAndMitigationId = riskAndMitigationId;
	}

	public String getRiskDesc() {
		return riskDesc;
	}

	public void setRiskDesc(String riskDesc) {
		this.riskDesc = riskDesc;
	}

	public String getMitigationDesc() {
		return mitigationDesc;
	}

	public void setMitigationDesc(String mitigationDesc) {
		this.mitigationDesc = mitigationDesc;
	}

	public Long getRiskAge() {
		return riskAge;
	}

	public void setRiskAge(Long riskAge) {
		this.riskAge = riskAge;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public ESummary geteSummary() {
		return eSummary;
	}

	public void seteSummary(ESummary eSummary) {
		this.eSummary = eSummary;
	}

	@Override
	public String toString() {
		return "ESummaryRiskAndMitigation [riskAndMitigationId=" + riskAndMitigationId + ", riskDesc=" + riskDesc
				+ ", mitigationDesc=" + mitigationDesc + ", riskAge=" + riskAge + ", status=" + status
				+ ", createdDate=" + createdDate + ", closedDate=" + closedDate + ", eSummary=" + eSummary + "]";
	}

}
