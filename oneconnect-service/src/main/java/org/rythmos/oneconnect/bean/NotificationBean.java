package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class NotificationBean extends AuditBean {
	private Long id;
	private ClientDetailBean clientDetail;
	private PortfolioBean portfolio;
	private ProjectDetailBean projectDetail;
	private Date startDate;
	private String reportType;
	private String operationType;
	private Boolean isMessage;
	private String room;
	private Long messageId;
	private String notificationBody;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClientDetailBean getClientDetail() {
		return clientDetail;
	}

	public void setClientDetail(ClientDetailBean clientDetail) {
		this.clientDetail = clientDetail;
	}

	public PortfolioBean getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioBean portfolio) {
		this.portfolio = portfolio;
	}

	public ProjectDetailBean getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(ProjectDetailBean projectDetail) {
		this.projectDetail = projectDetail;
	}

	public Boolean getIsMessage() {
		return isMessage;
	}

	public void setIsMessage(Boolean isMessage) {
		this.isMessage = isMessage;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getNotificationBody() {
		return notificationBody;
	}

	public void setNotificationBody(String notificationBody) {
		this.notificationBody = notificationBody;
	}

	@Override
	public String toString() {
		return "NotificationBean [id=" + id + ", clientDetail=" + clientDetail + ", portfolio=" + portfolio
				+ ", projectDetail=" + projectDetail + ", startDate=" + startDate + ", reportType=" + reportType
				+ ", operationType=" + operationType + ", isMessage=" + isMessage + ", room=" + room + ", messageId="
				+ messageId + ", notificationBody=" + notificationBody + "]";
	}

}
