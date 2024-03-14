package org.hyunseokcheong.userservice.dto;

import java.util.Date;
import java.util.List;

import org.hyunseokcheong.userservice.vo.ResponsePurchase;

import lombok.Data;

@Data
public class UserDto {
	
	private String email;
	private String name;
	private String password;
	private String userId;
	private Date createdAt;
	
	private String encryptedPassword;
	
	private List<ResponsePurchase> purchases;
}
