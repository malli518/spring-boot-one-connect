package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ESummaryBean {

	private Long eSummaryId;
	private ClientDetailBean clientDetail;
	private PortfolioBean portfolio;
	private ProjectDetailBean projectDetail;
	private Date eSummaryDate;
	private RagStatusBean ragStatus;
	private ESummaryAccomplishmentBean eSummaryAccomplishmentBean;
	private ESummaryRiskAndMitigationBean eSummaryRiskAndMitigationBean;
	private ESummarySummaryBean eSummarySummaryBean;

	public Long geteSummaryId() {
		return eSummaryId;
	}

	public void seteSummaryId(Long eSummaryId) {
		this.eSummaryId = eSummaryId;
	}

	public ClientDetailBean getClientDetail() {
		return clientDetail;
	}

	public void setClientDetail(ClientDetailBean clientDetail) {
		this.clientDetail = clientDetail;
	}

	public PortfolioBean getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioBean portfolio) {
		this.portfolio = portfolio;
	}

	public ProjectDetailBean getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(ProjectDetailBean projectDetail) {
		this.projectDetail = projectDetail;
	}

	public RagStatusBean getRagStatus() {
		return ragStatus;
	}

	public void setRagStatus(RagStatusBean ragStatus) {
		this.ragStatus = ragStatus;
	}

	public Date geteSummaryDate() {
		return eSummaryDate;
	}

	public void seteSummaryDate(Date eSummaryDate) {
		this.eSummaryDate = eSummaryDate;
	}

	public ESummaryAccomplishmentBean geteSummaryAccomplishmentBean() {
		return eSummaryAccomplishmentBean;
	}

	public void seteSummaryAccomplishmentBean(ESummaryAccomplishmentBean eSummaryAccomplishmentBean) {
		this.eSummaryAccomplishmentBean = eSummaryAccomplishmentBean;
	}

	public ESummaryRiskAndMitigationBean geteSummaryRiskAndMitigationBean() {
		return eSummaryRiskAndMitigationBean;
	}

	public void seteSummaryRiskAndMitigationBean(ESummaryRiskAndMitigationBean eSummaryRiskAndMitigationBean) {
		this.eSummaryRiskAndMitigationBean = eSummaryRiskAndMitigationBean;
	}

	public ESummarySummaryBean geteSummarySummaryBean() {
		return eSummarySummaryBean;
	}

	public void seteSummarySummaryBean(ESummarySummaryBean eSummarySummaryBean) {
		this.eSummarySummaryBean = eSummarySummaryBean;
	}

	@Override
	public String toString() {
		return "ESummaryBean [eSummaryId=" + eSummaryId + ", clientDetail=" + clientDetail + ", portfolio=" + portfolio
				+ ", projectDetail=" + projectDetail + ", eSummaryDate=" + eSummaryDate + ", ragStatus=" + ragStatus
				+ ", eSummaryAccomplishmentBean=" + eSummaryAccomplishmentBean + ", eSummaryRiskAndMitigationBean="
				+ eSummaryRiskAndMitigationBean + ", eSummarySummaryBean=" + eSummarySummaryBean + "]";
	}

}
