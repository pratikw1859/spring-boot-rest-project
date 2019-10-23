package com.app.rest.service;

import java.util.List;
import java.util.Optional;

import com.app.rest.model.User;

public interface IUserService {
	
	public List<User> getAllUsers();
	
	public User save(User user); 
	
	public Optional<User> getUserById(Long id); 
	
	public User updateUserById(Long id, User user);
	
	public void deleteUserById(Long id);
	
	public Optional<User> findByUsername(String username);
}
