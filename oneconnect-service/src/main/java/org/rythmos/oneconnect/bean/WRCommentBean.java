package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class WRCommentBean {

	private Long id;
	private String description;
	private WRBean weeklyReport;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public WRBean getWeeklyReport() {
		return weeklyReport;
	}

	public void setWeeklyReport(WRBean weeklyReport) {
		this.weeklyReport = weeklyReport;
	}

	@Override
	public String toString() {
		return "WRCommentBean [id=" + id + ", description=" + description + ", weeklyReport=" + weeklyReport + "]";
	}

}
