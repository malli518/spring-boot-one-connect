package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class ESummaryAccomplishmentBean {

	private Long accomplishmentId;
	private String description;
	private ESummaryBean eSummary;

	public Long getAccomplishmentId() {
		return accomplishmentId;
	}

	public void setAccomplishmentId(Long accomplishmentId) {
		this.accomplishmentId = accomplishmentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ESummaryBean geteSummary() {
		return eSummary;
	}

	public void seteSummary(ESummaryBean eSummary) {
		this.eSummary = eSummary;
	}

	@Override
	public String toString() {
		return "ESummaryAccomplishmentBean [accomplishmentId=" + accomplishmentId + ",description=" + description
				+ ", eSummary=" + eSummary + "]";
	}

}
