package com.app.rest.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> errors = new ArrayList<>();
		
		ex.getBindingResult().getFieldErrors()
				.forEach(fieldError -> errors.add(fieldError.getField() + ":" + fieldError.getDefaultMessage()));
		
		CustomErrorDetails details = new CustomErrorDetails(new Date(), "Validation Failed", errors);
		return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
		
		CustomErrorDetails details = new CustomErrorDetails(new Date(), ex.getLocalizedMessage(),
				Arrays.asList(request.getDescription(false)));
		
		return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
		List<String> errors = new ArrayList<>();
		
		Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
		
		constraintViolations.forEach(cv -> errors.add(cv.getPropertyPath()+" "+cv.getInvalidValue()+" "+cv.getMessage()));
		
		CustomErrorDetails details = new CustomErrorDetails(new Date(), ex.getLocalizedMessage(), errors);
		
		return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		CustomErrorDetails details = new CustomErrorDetails(new Date(), "Method Not Allowed",
				Arrays.asList(ex.getMessage()));

		return new ResponseEntity<>(details, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request){
		
		CustomErrorDetails details = new CustomErrorDetails(new Date(), ex.getMessage(), Arrays.asList(request.getDescription(false)));
		
		return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request){
		CustomErrorDetails details = new CustomErrorDetails(new Date(), ex.getMessage(), Arrays.asList(request.getDescription(false)));
		return new ResponseEntity<>(details,HttpStatus.NOT_FOUND);
	}
}