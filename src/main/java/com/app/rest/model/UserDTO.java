package com.app.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	
	private Long id;
	
	private String unName;
	
	private String name;
	
	private String emailAddress;
	
	private String role;
	
	private String ssn;
}
