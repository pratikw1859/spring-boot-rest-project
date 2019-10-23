package com.app.rest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.rest.exceptions.UserExistException;
import com.app.rest.exceptions.UserNotFoundException;
import com.app.rest.exceptions.UsernameNotFoundException;
import com.app.rest.model.Order;
import com.app.rest.model.User;
import com.app.rest.repository.UserRepository;
import com.app.rest.service.IOrderService;
import com.app.rest.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	private UserRepository userRepo;
	
	private IOrderService orderService;
	
	public UserServiceImpl(UserRepository userRepo, IOrderService orderService) {
		this.userRepo = userRepo;
		this.orderService = orderService;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	@Override
	public User save(User user) {
		Optional<User> optionalUsername = userRepo.findByUsername(user.getUsername());
		
		if(optionalUsername.isPresent()) {
			throw new UserExistException("User With Username:"+user.getUsername()+" Already Exist"); 
		}
		User savedInDb = userRepo.save(user);
		user.getOrders().stream().forEach(order-> order.setUser(savedInDb));
		List<Order> orders = orderService.saveAll(savedInDb.getOrders());
		savedInDb.setOrders(orders);
		return savedInDb;
	}
	
	@Override
	public Optional<User> getUserById(Long id) {
		Optional<User> user = userRepo.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("User With Id:"+id+" Not Found In User Repository");
		}
		return user;
	}
	
	@Override
	public User updateUserById(Long id, User user) {
		Optional<User> optionalUser = userRepo.findById(id);
		
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("User With Id:"+id+" Not Found, Provide The Valid Id"); 
		}
		
		user.setUserId(id);
		
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
		Optional<User> optionalUsername = userRepo.findByUsername(username);
		
		if(!optionalUsername.isPresent()) {
			throw new UsernameNotFoundException("Username:"+username+" Not Found, Plz Provide Valid Username");
		}
		
		return userRepo.findByUsername(username);
	}
}