package com.app.rest.hateoas;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.hateoas.Resources;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.rest.exceptions.OrderNotFoundException;
import com.app.rest.exceptions.UserNotFoundException;
import com.app.rest.model.Order;
import com.app.rest.model.User;
import com.app.rest.service.IOrderService;
import com.app.rest.service.IUserService;

@RestController
@RequestMapping(OrderHateoasController.API)
@Validated
public class OrderHateoasController {
	
	public static final String API = "/api/v1";
	
	private IUserService userService;
	
	private IOrderService orderService;
	
	public OrderHateoasController(IUserService userService, IOrderService orderService) {
		this.userService = userService;
		this.orderService = orderService;
	}

	@GetMapping("/hateoas/users/{id}/orders")
	public Resources<Order> getAllOrders(@PathVariable("id") @Min(1) Long userId) throws UserNotFoundException{
		Optional<User> optionalUser = userService.getUserById(userId);
		
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("User With Id:" + userId + " Not Found, Provide The Valid Id");
		}
		
		List<Order> orders = optionalUser.get().getOrders();
		
		Resources<Order> resources = new Resources<Order>(orders);
		
		return resources;
	}
	
	@GetMapping("/hateoas/{userId}/orders/{orderId}")
	public Order getOrderByOrderId(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) {
		Optional<User> optionalUser = userService.getUserById(userId);
		
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("User With Id:" + userId + " Not Found, Provide The Valid Id");
		}
		
		User user = optionalUser.get();
		
		Optional<Order> optionalOrder = orderService.getOrderByOrderId(orderId);
		if(!user.getOrders().contains(optionalOrder.get())) {
			throw new OrderNotFoundException("Order Not Found With id:" + orderId + " For User Id:" + userId);
		}
		return optionalOrder.get();
	}
}
