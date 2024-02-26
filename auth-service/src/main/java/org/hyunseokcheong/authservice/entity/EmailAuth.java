package org.hyunseokcheong.authservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;

@Getter
@RedisHash(value = "emailAuth", timeToLive = 600)
public class EmailAuth {

	@Id
	private String email;
	private String certificationCode;

	public EmailAuth(String email, String certificationCode) {
		this.email = email;
		this.certificationCode = certificationCode;
	}
}
