package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class WRBean extends AuditBean {
	private Long id;
	private ClientDetailBean clientDetail;
	private PortfolioBean portfolio;
	private ProjectDetailBean projectDetail;
	private Date wReportDate;
	private RagStatusBean ragStatus;
	private WRMajorUpdateBean wrMajorUpdateBean;
	private WRRiskAndMitigationBean wrRiskAndMitigationBean;
	private WRCommentBean wrCommentBean;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getwReportDate() {
		return wReportDate;
	}

	public void setwReportDate(Date wReportDate) {
		this.wReportDate = wReportDate;
	}

	public RagStatusBean getRagStatus() {
		return ragStatus;
	}

	public void setRagStatus(RagStatusBean ragStatus) {
		this.ragStatus = ragStatus;
	}

	public WRMajorUpdateBean getWrMajorUpdateBean() {
		return wrMajorUpdateBean;
	}

	public void setWrMajorUpdateBean(WRMajorUpdateBean wrMajorUpdateBean) {
		this.wrMajorUpdateBean = wrMajorUpdateBean;
	}

	public WRCommentBean getWrCommentBean() {
		return wrCommentBean;
	}

	public void setWrCommentBean(WRCommentBean wrCommentBean) {
		this.wrCommentBean = wrCommentBean;
	}

	public WRRiskAndMitigationBean getWrRiskAndMitigationBean() {
		return wrRiskAndMitigationBean;
	}

	public void setWrRiskAndMitigationBean(WRRiskAndMitigationBean wrRiskAndMitigationBean) {
		this.wrRiskAndMitigationBean = wrRiskAndMitigationBean;
	}

	@Override
	public String toString() {
		return "WRBean [id=" + id + ", clientDetail=" + clientDetail + ", portfolio=" + portfolio + ", projectDetail="
				+ projectDetail + ", wReportDate=" + wReportDate + ", ragStatus=" + ragStatus + ", wrMajorUpdateBean="
				+ wrMajorUpdateBean + ", wrRiskAndMitigationBean=" + wrRiskAndMitigationBean + ", wrCommentBean="
				+ wrCommentBean + "]";
	}

}
