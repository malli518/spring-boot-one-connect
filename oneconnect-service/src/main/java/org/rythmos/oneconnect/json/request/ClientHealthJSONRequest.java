package org.rythmos.oneconnect.json.request;

public class ClientHealthJSONRequest {

	private Long clientId;

	private Long portfolioId;

	private Long projectId;

	private String monthYear;

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

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	@Override
	public String toString() {
		return "ClientHealthJSONRequest [clientId=" + clientId + ", portfolioId=" + portfolioId + ", projectId="
				+ projectId + ", monthYear=" + monthYear + "]";
	}

}
