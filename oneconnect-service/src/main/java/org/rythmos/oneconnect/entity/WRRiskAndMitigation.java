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
@Table(name = "WRRiskAndMitigation", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "mitigationDesc", "riskDesc", "weekly_report_id" }) })
public class WRRiskAndMitigation extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(length = 380)
	private String riskDesc;

	@Column(length = 380)
	private String mitigationDesc;

	@ManyToOne
	private WeeklyReport WeeklyReport;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRiskDesc() {
		return riskDesc;
	}

	public void setRiskDesc(String riskDesc) {
		this.riskDesc = riskDesc;
	}

	public String getMitigationDesc() {
		return mitigationDesc;
	}

	public void setMitigationDesc(String mitigationDesc) {
		this.mitigationDesc = mitigationDesc;
	}

	public WeeklyReport getWeeklyReport() {
		return WeeklyReport;
	}

	public void setWeeklyReport(WeeklyReport weeklyReport) {
		WeeklyReport = weeklyReport;
	}

}
