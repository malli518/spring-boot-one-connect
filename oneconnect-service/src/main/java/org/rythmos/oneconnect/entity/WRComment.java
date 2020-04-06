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

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "description", "weekly_report_id" }) })
public class WRComment extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(length = 500)
	private String description;

	@ManyToOne
	private WeeklyReport WeeklyReport;

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

	public WeeklyReport getWeeklyReport() {
		return WeeklyReport;
	}

	public void setWeeklyReport(WeeklyReport weeklyReport) {
		WeeklyReport = weeklyReport;
	}

}
