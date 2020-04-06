package org.rythmos.oneconnect.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity

@Table(name = "ESummaryAccomplishment", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "description", "e_summary_e_summary_id" }) })
public class ESummaryAccomplishment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accomplishmentId", updatable = false, nullable = false)
	private Long accomplishmentId;

	@Column(length = 500)
	private String description;

	@ManyToOne
	private ESummary eSummary;

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

	public ESummary geteSummary() {
		return eSummary;
	}

	public void seteSummary(ESummary eSummary) {
		this.eSummary = eSummary;
	}

}
