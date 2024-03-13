package org.hyunseokcheong.userservice.service;

import org.hyunseokcheong.userservice.dto.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	
	UserDto getUserByUserId(String userId);
	
	Iterable<UserDto> getUserByAll();
}
