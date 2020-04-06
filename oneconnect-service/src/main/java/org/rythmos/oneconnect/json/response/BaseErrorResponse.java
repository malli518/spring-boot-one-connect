package org.rythmos.oneconnect.json.response;

import org.springframework.stereotype.Component;

@Component
public class BaseErrorResponse {
	String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "BaseErrorResponse [errorMessage=" + errorMessage + "]";
	}

}
