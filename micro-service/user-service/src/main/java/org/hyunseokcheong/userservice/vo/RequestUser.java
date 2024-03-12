package org.hyunseokcheong.userservice.vo;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestUser {
	
	@NotNull(message = "Email cannot be null")
	@Size(min = 2, message = "Email has to be longer than 2 characters")
	@Email(message = "Email has to be in the right format")
	private String email;
	
	@NotNull(message = "Name cannot be null")
	@Size(min = 2, message = "Name has to be longer than 2 characters")
	private String name;
	
	@NotNull(message = "Password cannot be null")
	@Size(min = 8, message = "Password has to be longer than 8 characters")
	private String password;
}
