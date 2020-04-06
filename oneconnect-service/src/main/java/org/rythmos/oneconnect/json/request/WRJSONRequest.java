package org.rythmos.oneconnect.json.request;

import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;
import org.rythmos.oneconnect.bean.RagStatusBean;
import org.rythmos.oneconnect.bean.WRValueObjectBean;

public class WRJSONRequest {

	private ClientDetailBean client;

	private PortfolioBean portfolio;

	private ProjectDetailBean project;

	private String startDate;

	private String endDate;

	private String transactionType;

	private RagStatusBean ragStatus;

	private WRValueObjectBean valueObject;

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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public RagStatusBean getRagStatus() {
		return ragStatus;
	}

	public void setRagStatus(RagStatusBean ragStatus) {
		this.ragStatus = ragStatus;
	}

	public WRValueObjectBean getValueObject() {
		return valueObject;
	}

	public void setValueObject(WRValueObjectBean valueObject) {
		this.valueObject = valueObject;
	}

	@Override
	public String toString() {
		return "WRJSONRequest [client=" + client + ", portfolio=" + portfolio + ", project=" + project + ", startDate="
				+ startDate + ", endDate=" + endDate + ", transactionType=" + transactionType + ", ragStatus="
				+ ragStatus + ", valueObject=" + valueObject + "]";
	}

}
