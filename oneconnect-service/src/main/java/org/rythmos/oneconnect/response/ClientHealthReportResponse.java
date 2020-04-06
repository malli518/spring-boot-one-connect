package org.rythmos.oneconnect.response;

import org.rythmos.oneconnect.json.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

public class ClientHealthReportResponse<T> extends GenericResponse<T> {

	public ClientHealthReportResponse(T body, MultiValueMap<String, String> headers, HttpStatus status) {
		super(body, headers, status);
	}

}