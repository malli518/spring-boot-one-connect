package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ExecutiveSummaryBean {

	private Long clientId;
	private Long portfolioId;
	private Long projectDetailId;
	private Date eSummaryDate;

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

	public Long getProjectDetailId() {
		return projectDetailId;
	}

	public void setProjectDetailId(Long projectDetailId) {
		this.projectDetailId = projectDetailId;
	}

	public Date geteSummaryDate() {
		return eSummaryDate;
	}

	public void seteSummaryDate(Date eSummaryDate) {
		this.eSummaryDate = eSummaryDate;
	}

	@Override
	public String toString() {
		return "ExecutiveSummaryBean [clientId=" + clientId + ", portfolioId=" + portfolioId + ", projectDetailId="
				+ projectDetailId + ", eSummaryDate=" + eSummaryDate + "]";
	}

}
