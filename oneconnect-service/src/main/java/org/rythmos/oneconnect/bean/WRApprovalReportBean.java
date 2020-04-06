package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class WRApprovalReportBean extends AuditBean {

	private Long id;
	private Long productQuality;
	private Long codeQuality;
	private Boolean approvalStatus;
	private WRBean weeklyReport;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductQuality() {
		return productQuality;
	}

	public void setProductQuality(Long productQuality) {
		this.productQuality = productQuality;
	}

	public Long getCodeQuality() {
		return codeQuality;
	}

	public void setCodeQuality(Long codeQuality) {
		this.codeQuality = codeQuality;
	}

	public Boolean getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public WRBean getWeeklyReport() {
		return weeklyReport;
	}

	public void setWeeklyReport(WRBean weeklyReport) {
		this.weeklyReport = weeklyReport;
	}

	@Override
	public String toString() {
		return "WRApprovalReportBean [id=" + id + ", productQuality=" + productQuality + ", codeQuality=" + codeQuality
				+ ", approvalStatus=" + approvalStatus + ", weeklyReport=" + weeklyReport + "]";
	}

}
