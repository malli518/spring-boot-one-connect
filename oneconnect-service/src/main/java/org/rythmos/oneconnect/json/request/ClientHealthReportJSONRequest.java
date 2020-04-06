package org.rythmos.oneconnect.json.request;

import java.util.List;

import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.HealthIndicatorBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;

public class ClientHealthReportJSONRequest {

	private ClientDetailBean client;

	private PortfolioBean portfolio;

	private ProjectDetailBean project;

	private String monthYear;

	private List<HealthIndicatorBean> healthIndicators;

	public ClientDetailBean getClient() {
		return client;
	}

	public void setClient(ClientDetailBean client) {
		this.client = client;
	}

	public PortfolioBean getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioBean portfolio) {
		this.portfolio = portfolio;
	}

	public ProjectDetailBean getProject() {
		return project;
	}

	public void setProject(ProjectDetailBean project) {
		this.project = project;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public List<HealthIndicatorBean> getHealthIndicators() {
		return healthIndicators;
	}

	public void setHealthIndicators(List<HealthIndicatorBean> healthIndicators) {
		this.healthIndicators = healthIndicators;
	}

	@Override
	public String toString() {
		return "ClientHealthReportJSONRequest [client=" + client + ", portfolio=" + portfolio + ", project=" + project
				+ ", monthYear=" + monthYear + ", healthIndicators=" + healthIndicators + "]";
	}

}
