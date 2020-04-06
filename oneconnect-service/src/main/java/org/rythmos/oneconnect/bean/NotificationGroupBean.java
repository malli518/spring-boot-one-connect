package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class NotificationGroupBean extends AuditBean {
	private Long id;
	private Boolean isRead;
	private NotificationBean notification;
	private UserBean user;

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

	public NotificationBean getNotification() {
		return notification;
	}

	public void setNotification(NotificationBean notification) {
		this.notification = notification;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "NotificationGroupBean [id=" + id + ", isRead=" + isRead + ", notification=" + notification + ", user="
				+ user + "]";
	}

}
