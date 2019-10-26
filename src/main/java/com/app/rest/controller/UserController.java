package com.app.rest.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.rest.model.User;
import com.app.rest.service.IUserService;

@RestController
@RequestMapping(UserController.API)
@Validated
public class UserController {
	
	public static final String API = "/api/v1/users";
	
	private IUserService userService;

	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody User user){
		
		User savedInDb = userService.save(user);		
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedInDb.getId()).toUri();
		
		return ResponseEntity.created(location).body(savedInDb);
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") @Min(1)Long id){
		return userService.getUserById(id).get();
	}
	
	@PutMapping("/{id}")
	public User updateUserById(@PathVariable("id") Long id ,@Valid @RequestBody User user){
		return userService.updateUserById(id, user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id")Long id){
		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/byUserName/{username}")
	public User findByUsername(@PathVariable("username")String username){
		return userService.findByUsername(username).get();
	}
}