package com.app.rest.mapper.mapstruct;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.app.rest.model.User;
import com.app.rest.model.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mappings({
		@Mapping(source = "userId" , target = "id"),
		@Mapping(source = "username" , target = "unName"),
		@Mapping(target = "name" , expression = "java(user.getFirstName()+ \" \"+ user.getLastName())"),
		@Mapping(source = "email" , target = "emailAddress"),
		@Mapping(source = "ssn", target = "ssn"),
		@Mapping(source = "role", target = "role")
	})
	public UserDTO userToUserDTO(User user);

	@InheritInverseConfiguration
	@Mapping(target = "firstName" , expression = "java(splitNames(dto,0))")
	@Mapping(target = "lastName" , expression = "java(splitNames(dto,1))")
	public User userDTOToUser(UserDTO dto);
	
	//@InheritConfiguration(name = "userToUserDTO")
	public List<UserDTO> listUserToUserDTOs(List<User> users);
	
	default String splitNames(UserDTO dto, int index) {
		String[] split = dto.getName().split(" ");
		
		List<String> collect = Arrays.stream(split).collect(Collectors.toList());
		
		return collect.get(index);
	}
}