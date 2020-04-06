package org.rythmos.oneconnect.json.response;

import java.util.Date;

import org.rythmos.oneconnect.bean.AuditBean;

public class NotificationGroupResponse extends AuditBean {

	private Long id;
	private Boolean isRead;
	private Long clientId;
	private Long portfolioId;
	private Long projectId;
	private Date startDate;
	private String notificationBody;
	private String operationType;
	private String reportType;
	private Boolean isMessage;
	private Long messageId;
	private String room;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId) {
		this.portfolioId = portfolioId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getNotificationBody() {
		return notificationBody;
	}

	public void setNotificationBody(String notificationBody) {
		this.notificationBody = notificationBody;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
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

	@Override
	public String toString() {
		return "NotificationGroupResponse [id=" + id + ", isRead=" + isRead + ", clientId=" + clientId
				+ ", portfolioId=" + portfolioId + ", projectId=" + projectId + ", startDate=" + startDate
				+ ", notificationBody=" + notificationBody + ", operationType=" + operationType + ", reportType="
				+ reportType + ", isMessage=" + isMessage + ", messageId=" + messageId + ", room=" + room + "]";
	}

}
