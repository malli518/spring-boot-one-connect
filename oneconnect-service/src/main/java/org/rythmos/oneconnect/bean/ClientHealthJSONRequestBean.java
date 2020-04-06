package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ClientHealthJSONRequestBean {

	private Long clientId;

	private Long portfolioId;

	private Long projectId;

	private Date monthYear;

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

	public Date getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(Date monthYear) {
		this.monthYear = monthYear;
	}

	@Override
	public String toString() {
		return "ClientHealthJSONRequestBean [clientId=" + clientId + ", portfolioId=" + portfolioId + ", projectId="
				+ projectId + ", monthYear=" + monthYear + "]";
	}

}
