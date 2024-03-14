package org.hyunseokcheong.userservice.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
	
	private String email;
	private String name;
	private String userId;
	
	private List<ResponsePurchase> purchases;
}
