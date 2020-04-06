package org.rythmos.oneconnect.json.response;

import java.util.Date;

public class ReportDetailJSONResponse {

	private Long clientId;
	private Long projectId;
	private Long portfolioId;
	private Date startDate;
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(Long portfolioId) {
		this.portfolioId = portfolioId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Override
	public String toString() {
		return "ReportDetailJSONResponse [clientId=" + clientId + ", projectId=" + projectId + ", portfolioId="
				+ portfolioId + ", startDate=" + startDate + "]";
	}
	
	
}
