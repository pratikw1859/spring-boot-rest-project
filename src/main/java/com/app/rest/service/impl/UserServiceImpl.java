package com.app.rest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.rest.exceptions.UserExistException;
import com.app.rest.exceptions.UserNotFoundException;
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
	public User save(User user) throws UserExistException{
		Optional<User> optionalUsername = userRepo.findByUsername(user.getUsername());
		
		if(optionalUsername.isPresent()) {
			throw new UserExistException("User With Username:"+user.getUsername()+" Already Exist"); 
		}
		return userRepo.save(user);
	}
	
	@Override
	public Optional<User> getUserById(Long id) throws UserNotFoundException {
		Optional<User> user = userRepo.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("User With Id:"+id+" Not Found In User Repository");
		}
		return user;
	}
	
	@Override
	public User updateUserById(Long id, User user) throws UserNotFoundException{
		Optional<User> optionalUser = userRepo.findById(id);
		
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("User With Id:"+id+" Not Found, Provide The Valid Id"); 
		}
		
		user.setId(id);
		
		return userRepo.save(user);
	}
	
	@Override
	public void deleteUserById(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);

		if(!optionalUser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User With Id:"+id+" Not Found, Provide The Valid Id");
		}
		
		userRepo.deleteById(id);
	}
	
	@Override
	public Optional<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
}