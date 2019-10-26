package com.app.rest.filter.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.rest.filter.util.DynamicFilterUtil;
import com.app.rest.model.User;
import com.app.rest.service.IUserService;

@RestController
@RequestMapping(UserFilterController.API)
@Validated
public class UserFilterController {
	
	public static final String API = "/api/v1/filters/users";
	
	private IUserService userService;

	public UserFilterController(IUserService userService) {
		super();
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
		
		Set<String> setFilters = new HashSet<String>(Arrays.asList("userId","firstName","lastName","email"));
		MappingJacksonValue jacksonValue = DynamicFilterUtil.dynamicFiltering(Optional.ofNullable(savedInDb), "userFilter", setFilters);
		
		return ResponseEntity.created(location).body(jacksonValue);
	}

	@GetMapping("/{id}")
	public MappingJacksonValue getUserById(@PathVariable("id") @Min(1)Long id){
		Optional<User> optionalUser = userService.getUserById(id);
		
		Set<String> setFilters = new HashSet<String>(Arrays.asList("userId","firstName","lastName","email"));
		MappingJacksonValue jacksonValue = DynamicFilterUtil.dynamicFiltering(optionalUser, "userFilter", setFilters);
		
		return jacksonValue;
	}
	
	
	@GetMapping("/byUserName/{username}")
	public ResponseEntity<MappingJacksonValue> findByUsername(@PathVariable("username")String username){
		Optional<User> optionalUser = userService.findByUsername(username);
		
		Set<String> filters = new HashSet<>(Arrays.asList("username","firstName","lastName","email"));
		MappingJacksonValue jacksonValue = DynamicFilterUtil.dynamicFiltering(optionalUser, "userFilter", filters);
		
		return ResponseEntity.ok(jacksonValue);
	}
}
