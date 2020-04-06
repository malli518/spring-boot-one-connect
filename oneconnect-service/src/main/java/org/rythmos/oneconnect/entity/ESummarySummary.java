package org.rythmos.oneconnect.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.rythmos.oneconnect.audit.Auditable;

@Entity

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "description", "e_summary_e_summary_id" }) })
public class ESummarySummary extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "summaryId", updatable = false, nullable = false)
	private Long summaryId;

	@Column(length = 500)
	private String description;

	@ManyToOne
	private ESummary eSummary;

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

	public ESummary geteSummary() {
		return eSummary;
	}

	public void seteSummary(ESummary eSummary) {
		this.eSummary = eSummary;
	}

	@Override
	public String toString() {
		return "ESummarySummary [summaryId=" + summaryId + ",description=" + description + ", eSummary=" + eSummary
				+ "]";
	}

}
