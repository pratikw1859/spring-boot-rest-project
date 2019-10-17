package com.app.rest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.rest.model.User;
import com.app.rest.repository.UserRepository;
import com.app.rest.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	private UserRepository userRepo;
	
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	@Override
	public User save(User user) {
		return userRepo.save(user);
	}
	
	@Override
	public Optional<User> getUserById(Long id) {
		return userRepo.findById(id);
	}
	
	@Override
	public User updateUserById(Long id, User user) {
		user.setId(id);
		return userRepo.save(user);
	}
	
	@Override
	public void deleteUserById(Long id) {
		if(userRepo.findById(id).isPresent()) {
			userRepo.deleteById(id);
		}
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
}
