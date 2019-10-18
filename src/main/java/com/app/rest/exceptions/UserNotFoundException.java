package com.app.rest.exceptions;

public class UserNotFoundException extends Exception{

	private static final long serialVersionUID = -1344485680673237356L;
	
	public UserNotFoundException(String message) {
		super(message);
	}
}