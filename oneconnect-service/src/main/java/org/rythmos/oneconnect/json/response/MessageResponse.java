package org.rythmos.oneconnect.json.response;

import java.util.Date;

import org.rythmos.oneconnect.bean.SenderBean;

public class MessageResponse {

	public MessageResponse(Long id, String message, Boolean isEdited, Date time, SenderBean sender) {
		this.id = id;
		this.message = message;
		this.isEdited = isEdited;
		this.time = time;
		this.sender = sender;
	}

	private Long id;

	private String message;

	private Boolean isEdited;

	private Date time;

	private SenderBean sender;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Boolean isEdited) {
		this.isEdited = isEdited;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public SenderBean getSender() {
		return sender;
	}

	public void setSender(SenderBean sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "MessageResponse [id=" + id + ", message=" + message + ", isEdited=" + isEdited + ", time=" + time
				+ ", sender=" + sender + "]";
	}

}
