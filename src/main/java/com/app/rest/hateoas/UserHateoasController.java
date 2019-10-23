package com.app.rest.hateoas;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.rest.model.User;
import com.app.rest.service.IUserService;

@RestController
@RequestMapping(UserHateoasController.API)
@Validated
public class UserHateoasController {
	
	public static final String API = "/api/v1";
	
	private IUserService userService;

	public UserHateoasController(IUserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/hateoas/users/{id}")
	public Resource<User> getUserById(@PathVariable("id")@Min(1) Long userId){
		User user = userService.getUserById(userId).get();

		Link withSelfRel = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getUserById(userId)).withSelfRel();
		Link withRel = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllUsers()).withRel("get-All-Users");
		
		user.add(withSelfRel,withRel);
		
		Resource<User> resource = new Resource<User>(user);

		return resource;
	}
	
	@GetMapping("/hateoas/users")
	public Resources<User> getAllUsers(){
		List<User> users = userService.getAllUsers();

		for(User user : users) {
			
			//With-Self Link
			Link withSelfRel = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getUserById(user.getUserId())).withSelfRel();
			user.add(withSelfRel);
			
			Link orderLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(user.getUserId())).withRel("get-All-Orders");
			user.add(orderLink);
		}
		
		Resources<User> resources = new Resources<User>(users);

		return resources;
	}
}
