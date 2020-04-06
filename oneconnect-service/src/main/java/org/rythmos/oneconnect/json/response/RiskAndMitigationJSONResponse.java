package org.rythmos.oneconnect.json.response;

public class RiskAndMitigationJSONResponse {

	private Long id;
	private String risk;
	private String mitigation;
	private Long riskAge;
	private Boolean status;
	private String createdDate;
	private String closedDate;
	private String projectName;
	private String reportType;
	private ReportDetailJSONResponse reportDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getMitigation() {
		return mitigation;
	}

	public void setMitigation(String mitigation) {
		this.mitigation = mitigation;
	}

	public Long getRiskAge() {
		return riskAge;
	}

	public void setRiskAge(Long riskAge) {
		this.riskAge = riskAge;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}
	
	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public ReportDetailJSONResponse getReportDetails() {
		return reportDetails;
	}

	public void setReportDetails(ReportDetailJSONResponse reportDetails) {
		this.reportDetails = reportDetails;
	}

	@Override
	public String toString() {
		return "RiskAndMitigationJSONResponse [id=" + id + ", risk=" + risk + ", mitigation=" + mitigation
				+ ", riskAge=" + riskAge + ", status=" + status + ", createdDate=" + createdDate + ", closedDate="
				+ closedDate + ", projectName=" + projectName + ", reportType=" + reportType + ", reportDetails="
				+ reportDetails + "]";
	}

}
