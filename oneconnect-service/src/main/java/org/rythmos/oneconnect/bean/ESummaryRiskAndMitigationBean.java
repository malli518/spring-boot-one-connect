package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ESummaryRiskAndMitigationBean {

	private Long riskAndMitigationId;
	private String riskDesc;
	private String mitigationDesc;
	private Long riskAge;
	private String status;
	private Date createdDate;
	private Date closedDate;

	private ESummaryBean eSummary;

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

	public ESummaryBean geteSummary() {
		return eSummary;
	}

	public void seteSummary(ESummaryBean eSummary) {
		this.eSummary = eSummary;
	}

	@Override
	public String toString() {
		return "ESummaryRiskAndMitigationBean [riskAndMitigationId=" + riskAndMitigationId + ", riskDesc=" + riskDesc
				+ ", mitigationDesc=" + mitigationDesc + ", riskAge=" + riskAge + ", status=" + status
				+ ", createdDate=" + createdDate + ", closedDate=" + closedDate + ", eSummary=" + eSummary + "]";
	}

}
