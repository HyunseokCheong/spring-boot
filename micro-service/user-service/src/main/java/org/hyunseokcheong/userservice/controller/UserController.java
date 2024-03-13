package org.hyunseokcheong.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.hyunseokcheong.userservice.dto.UserDto;
import org.hyunseokcheong.userservice.service.UserService;
import org.hyunseokcheong.userservice.vo.RequestUser;
import org.hyunseokcheong.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
	
	private Environment environment;
	private UserService userService;
	
	@Autowired
	public UserController(Environment environment, UserService userService) {
		this.environment = environment;
		this.userService = userService;
	}
	
	@GetMapping("/health_check")
	public String status() {
		return "It's Working in User Service on PORT " + environment.getProperty("local.server.port");
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return environment.getProperty("greeting.message");
	}
	
	@PostMapping
	public ResponseEntity<Object> createUser(@RequestBody RequestUser requestUser) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(requestUser, UserDto.class);
		UserDto returnUserDto = userService.createUser(userDto);
		
		ResponseUser responseUser = modelMapper.map(returnUserDto, ResponseUser.class);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(responseUser);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getUserByUserId(@PathVariable("userId") String userId) {
		UserDto userDto = userService.getUserByUserId(userId);
		
		ResponseUser responseUser = new ModelMapper().map(userDto, ResponseUser.class);
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(responseUser);
	}
	
	@GetMapping
	public ResponseEntity<Object> getUsers() {
		Iterable<UserDto> users = userService.getUserByAll();
		
		List<ResponseUser> result = new ArrayList<>();
		users.forEach(v -> result.add(new ModelMapper().map(v, ResponseUser.class)));
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(result);
	}
}
