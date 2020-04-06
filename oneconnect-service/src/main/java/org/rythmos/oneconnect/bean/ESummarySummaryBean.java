package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class ESummarySummaryBean {

	private Long summaryId;
	private String description;
	private ESummaryBean eSummary;

	public Long getSummaryId() {
		return summaryId;
	}

	public void setSummaryId(Long summaryId) {
		this.summaryId = summaryId;
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
		return "ESummarySummaryBean [summaryId=" + summaryId + ", description=" + description + ", eSummary=" + eSummary
				+ "]";
	}

}
