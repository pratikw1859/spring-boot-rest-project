package com.app.rest.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.rest.exceptions.OrderNotFoundException;
import com.app.rest.exceptions.UserNotFoundException;
import com.app.rest.model.Order;
import com.app.rest.model.User;
import com.app.rest.repository.OrderRepository;
import com.app.rest.repository.UserRepository;

@RestController
@RequestMapping(UserController.API)
public class OrderController {

	public static final String API = "/api/v1/users";

	private UserRepository userRepo;

	private OrderRepository orderRepo;

	public OrderController(UserRepository userRepo, OrderRepository orderRepo) {
		this.userRepo = userRepo;
		this.orderRepo = orderRepo;
	}

	@GetMapping("/{userId}/orders")
	public List<Order> getAllOrder(@PathVariable("userId") Long userId) throws UserNotFoundException {
		Optional<User> optionalUser = userRepo.findById(userId);

		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User With Id:" + userId + " Not Found, Provide The Valid Id");
		}
		return optionalUser.get().getOrders();
	}

	@PostMapping("/{userId}/orders")
	public ResponseEntity<Order> save(@PathVariable("userId") Long userId, @RequestBody Order order)
			throws UserNotFoundException {
		Optional<User> optionalUser = userRepo.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User With Id:" + userId + " Not Found, Provide The Valid Id");
		}

		User user = optionalUser.get();
		order.setUser(user);
		Order savedInDb = orderRepo.save(order);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{orderId}")
				.buildAndExpand(savedInDb.getOrderId()).toUri();

		return ResponseEntity.created(location).body(savedInDb);
	}

	@GetMapping("/{userId}/orders/{orderId}")
	public Order getOrderByOrderId(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId)
			throws UserNotFoundException {
		Optional<User> optionalUser = userRepo.findById(userId);

		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User With Id:" + userId + " Not Found, Provide The Valid Id");
		}

		User user = optionalUser.get();

		Optional<Order> optionalOrder = orderRepo.findById(orderId);

		if (!user.getOrders().contains(optionalOrder.get())) {
			throw new OrderNotFoundException("Order Not Found With id:" + orderId + " For User Id:" + userId);
		}

		return optionalOrder.get();
	}
}
