package org.rythmos.oneconnect.json.request;

import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.ESValueObjectBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;
import org.rythmos.oneconnect.bean.RagStatusBean;

public class ESJSONRequest {

	private ClientDetailBean client;

	private PortfolioBean portfolio;

	private ProjectDetailBean project;

	private String startDate;

	private String endDate;

	private String transactionType;

	private RagStatusBean ragStatus;

	private ESValueObjectBean valueObject;

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

	public RagStatusBean getRagStatus() {
		return ragStatus;
	}

	public void setRagStatus(RagStatusBean ragStatus) {
		this.ragStatus = ragStatus;
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

	public ESValueObjectBean getValueObject() {
		return valueObject;
	}

	public void setValueObject(ESValueObjectBean valueObject) {
		this.valueObject = valueObject;
	}

	@Override
	public String toString() {
		return "ESJSONRequest [client=" + client + ", portfolio=" + portfolio + ", project=" + project + ", startDate="
				+ startDate + ", endDate=" + endDate + ", transactionType=" + transactionType + ", ragStatus="
				+ ragStatus + ", valueObject=" + valueObject + "]";
	}

}
