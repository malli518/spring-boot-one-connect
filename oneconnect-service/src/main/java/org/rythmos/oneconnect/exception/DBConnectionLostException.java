package org.rythmos.oneconnect.exception;

public class DBConnectionLostException extends OneConnectApplicationException {

	private static final long serialVersionUID = -5458170537685759413L;

	public DBConnectionLostException(String message) {
		super(message);
	}
}
