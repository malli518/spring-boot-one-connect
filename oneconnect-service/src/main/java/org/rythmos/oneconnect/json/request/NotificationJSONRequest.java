package org.rythmos.oneconnect.json.request;

import org.springframework.stereotype.Component;

@Component
public class NotificationJSONRequest {

	private Long clientId;

	private Long portfolioId;

	private Long projectId;

	private String startDate;

	private String reportType;

	private String operationType;

	private String notificationBody;

	private Boolean isMessage;

	private String room;

	private Long messageId;

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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
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
		return "NotificationJSONRequest [clientId=" + clientId + ", portfolioId=" + portfolioId + ", projectId="
				+ projectId + ", startDate=" + startDate + ", reportType=" + reportType + ", operationType="
				+ operationType + ", notificationBody=" + notificationBody + ", isMessage=" + isMessage + ", room="
				+ room + ", messageId=" + messageId + "]";
	}

}
