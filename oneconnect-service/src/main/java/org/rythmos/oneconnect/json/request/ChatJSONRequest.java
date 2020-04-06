package org.rythmos.oneconnect.json.request;

import org.rythmos.oneconnect.bean.MessageBean;

public class ChatJSONRequest {

	private String room;
	private MessageBean message;

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public MessageBean getMessage() {
		return message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ChatJSONRequest [room=" + room + ", message=" + message + "]";
	}

}
