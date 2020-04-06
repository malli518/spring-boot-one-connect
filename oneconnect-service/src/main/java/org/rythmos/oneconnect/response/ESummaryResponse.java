package org.rythmos.oneconnect.response;

import org.rythmos.oneconnect.json.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

public class ESummaryResponse<T> extends GenericResponse<T> {

	public ESummaryResponse(T body, MultiValueMap<String, String> headers, HttpStatus status) {
		super(body, headers, status);
	}
}
