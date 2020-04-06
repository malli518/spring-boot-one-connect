package org.rythmos.oneconnect.json.response;

public class WRApprovalReportJSONResponse {

	private Long projectQuality;

	private Long codeQuality;

	private Boolean approvalStatus;

	public Long getProjectQuality() {
		return projectQuality;
	}

	public void setProjectQuality(Long projectQuality) {
		this.projectQuality = projectQuality;
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

	@Override
	public String toString() {
		return "WRApprovalReportJSONResponse [projectQuality=" + projectQuality + ", codeQuality=" + codeQuality
				+ ", approvalStatus=" + approvalStatus + "]";
	}

}
