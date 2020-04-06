package org.rythmos.oneconnect.json.response;

public class ProjectJSONResponse {

	private String projectName;
	private String reportType;
	private ReportDetailJSONResponse reportDetails;

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
	
	

}
