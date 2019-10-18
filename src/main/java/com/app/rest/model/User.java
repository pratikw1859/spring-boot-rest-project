package com.app.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "USERNAME" , length = 50 , nullable = false , unique = true)
	@NotEmpty(message = "Username Is Mandatory Field, Plz Provide Username")
	private String username;
	
	@Column(name = "FIRSTNAME" , length = 50 , nullable = false)
	@Size(min = 2, message = "Firstname Should Have Atleast 2 Characters")
	private String firstName;
	
	@Column(name = "LASTNAME" , length = 50 , nullable = false)
	private String lastName;
	
	@Column(name = "EMAIL" , length = 50 , nullable = false)
	private String email;
		
	@Column(name = "ROLE" , length = 50 , nullable = false)
	private String role;
	
	@Column(name = "SSN" , length = 50 , nullable = false , unique = true)
	private String ssn;
}