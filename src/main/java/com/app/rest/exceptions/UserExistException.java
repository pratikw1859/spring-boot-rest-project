package com.app.rest.exceptions;

public class UserExistException extends Exception {

	private static final long serialVersionUID = -7380138428358945165L;

	public UserExistException(String message) {
		super(message);
	}
}
