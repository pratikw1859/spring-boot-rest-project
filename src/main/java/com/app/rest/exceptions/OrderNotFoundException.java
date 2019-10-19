package com.app.rest.exceptions;

public class OrderNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -6931837696993760468L;

	public OrderNotFoundException(String message) {
		super(message);
	}
}
