package org.rythmos.oneconnect.bean;

public class ChatMessageBean extends AuditBean {
	private long id;

	private String room;

	private Boolean isEdited;

	private String message;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Boolean getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Boolean isEdited) {
		this.isEdited = isEdited;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ChatJSONBean [id=" + id + ", room=" + room + ", isEdited=" + isEdited + ", message=" + message + "]";
	}

}
