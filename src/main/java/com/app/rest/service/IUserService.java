package com.app.rest.service;

import java.util.List;
import java.util.Optional;

import com.app.rest.exceptions.UserExistException;
import com.app.rest.exceptions.UserNotFoundException;
import com.app.rest.model.User;

public interface IUserService {
	
	public List<User> getAllUsers();
	
	public User save(User user) throws UserExistException;
	
	public Optional<User> getUserById(Long id) throws UserNotFoundException;
	
	public User updateUserById(Long id, User user) throws UserNotFoundException;
	
	public void deleteUserById(Long id);
	
	public Optional<User> findByUsername(String username);
}
