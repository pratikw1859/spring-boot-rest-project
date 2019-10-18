package com.app.rest.exceptions;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorDetails {
	
	private Date timestamp;
	
	private String message;
	
	private List<String> errors;
	
}
