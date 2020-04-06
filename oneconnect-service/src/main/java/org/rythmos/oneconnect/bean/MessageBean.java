package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class MessageBean {
	private SenderBean sender;

	private Boolean isEdited;

	private Long id;

	private String time;

	private String message;

	public SenderBean getSender() {
		return sender;
	}

	public void setSender(SenderBean sender) {
		this.sender = sender;
	}

	public Boolean getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Boolean isEdited) {
		this.isEdited = isEdited;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MessageBean [sender=" + sender + ", isEdited=" + isEdited + ", id=" + id + ", time=" + time
				+ ", message=" + message + "]";
	}

}
