package com.app.rest.exceptions;

public class UsernameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7093614441731080296L;
	
	public UsernameNotFoundException(String message) {
		super(message);
	}
}
