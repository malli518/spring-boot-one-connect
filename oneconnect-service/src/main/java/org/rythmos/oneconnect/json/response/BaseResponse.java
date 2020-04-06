package org.rythmos.oneconnect.json.response;

import org.springframework.stereotype.Component;

@Component
public class BaseResponse {
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Response [message=" + message + "]";
	}

}
