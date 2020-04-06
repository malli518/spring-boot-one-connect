package org.rythmos.oneconnect.json.request;

public class WeeklyReportJSONRequest {

	private Long clientId;

	private Long portfolioId;

	private Long projectId;

	private String startDate;

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId) {
		this.portfolioId = portfolioId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "WeeklyReportJSONRequest [clientId=" + clientId + ", portfolioId=" + portfolioId + ", projectId="
				+ projectId + ", startDate=" + startDate + "]";
	}

}
