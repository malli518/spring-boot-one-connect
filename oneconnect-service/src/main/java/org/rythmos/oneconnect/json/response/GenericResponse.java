package org.rythmos.oneconnect.json.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

public class GenericResponse<T> extends ResponseEntity<T> {

	public GenericResponse(HttpStatus status) {
		super(status);
	}

	public GenericResponse(@Nullable T body, HttpStatus status) {
		super(body, null, status);
	}

	public GenericResponse(MultiValueMap<String, String> headers, HttpStatus status) {
		super(null, headers, status);
	}

	public GenericResponse(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatus status) {
		super(body, headers, status);
		Assert.notNull(status, "HttpStatus must not be null");
	}

}
