package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class WeeklyReportBean {
	private Long clientId;
	private Long portfolioId;
	private Long projectDetailId;
	private Date wReportDate;

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

	public Date getwReportDate() {
		return wReportDate;
	}

	public void setwReportDate(Date wReportDate) {
		this.wReportDate = wReportDate;
	}

	@Override
	public String toString() {
		return "WeeklyReportBean [clientId=" + clientId + ", portfolioId=" + portfolioId + ", projectDetailId="
				+ projectDetailId + ", wReportDate=" + wReportDate + "]";
	}

}
