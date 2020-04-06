package org.rythmos.oneconnect.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.rythmos.oneconnect.audit.Auditable;

@Entity
@Table
public class WRApprovalReport extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)

	private Long id;

	@Column
	private Long projectQuality;

	@Column
	private Long codeQuality;

	@Column
	private Boolean approvalStatus;

	@ManyToOne
	private WeeklyReport WeeklyReport;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public WeeklyReport getWeeklyReport() {
		return WeeklyReport;
	}

	public void setWeeklyReport(WeeklyReport weeklyReport) {
		WeeklyReport = weeklyReport;
	}

	@Override
	public String toString() {
		return "WRApprovalReport [id=" + id + ", projectQuality=" + projectQuality + ", codeQuality=" + codeQuality
				+ ", approvalStatus=" + approvalStatus + ", WeeklyReport=" + WeeklyReport + "]";
	}

}
