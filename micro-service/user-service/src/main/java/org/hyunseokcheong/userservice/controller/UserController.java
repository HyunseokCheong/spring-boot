package org.hyunseokcheong.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
	
	private Environment environment;
	
	@Autowired
	public UserController(Environment environment) {
		this.environment = environment;
	}
	
	@GetMapping("/health_check")
	public String status() {
		return "It's Working in User Service on PORT " + environment.getProperty("local.server.port");
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return environment.getProperty("greeting.message");
	}
}
