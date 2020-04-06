package org.rythmos.oneconnect.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "projectName" }) })
public class ProjectDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@NotNull
	@NotBlank
	@Size(max = 100)
	private String projectName;

	@Column
	private Date projectStartDate;

	@Column
	private Date projectEndDate;

	@Column
	private String rythmosSM;

	@Column
	private String productOwner;

	@Column
	private String manager;

	@Column
	private String director;

	@Column(columnDefinition = "TEXT")
	public String purpose;

	@ManyToOne
	private Portfolio portfolio;

	public String getRythmosSM() {
		return rythmosSM;
	}

	public void setRythmosSM(String rythmosSM) {
		this.rythmosSM = rythmosSM;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Long getId() {
		return id;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public Date getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	@Override
	public String toString() {
		return "ProjectDetail [id=" + id + ", projectName=" + projectName + ", projectStartDate=" + projectStartDate
				+ ", projectEndDate=" + projectEndDate + ", rythmosSM=" + rythmosSM + ", productOwner=" + productOwner
				+ ", manager=" + manager + ", director=" + director + ", purpose=" + purpose + ", portfolio="
				+ portfolio + "]";
	}

}
