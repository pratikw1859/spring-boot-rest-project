package com.app.rest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
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
public class UserController {
	
	public static final String API = "/api/v1";
	
	private IUserService userService;

	public UserController(IUserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> save(@RequestBody User user){
		User savedInDb = userService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedInDb.getId()).toUri();
		return ResponseEntity.created(location).body(savedInDb);
	}
	
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable("id")Long id){
		return userService.getUserById(id).get();
	}
	
	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable("id") Long id , @RequestBody User user){
		return userService.updateUserById(id, user);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id")Long id){
		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/users/byUserName/{username}")
	public User findByUsername(@PathVariable("username")String username){
		return userService.findByUsername(username);
	}
}