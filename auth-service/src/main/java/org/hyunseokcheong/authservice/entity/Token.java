package org.hyunseokcheong.authservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;

@Getter
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 3)
public class Token {

	@Id
	private Long id;

	@Indexed
	private Long accountId;

	@Indexed
	private String accessToken;

	private String refreshToken;

	public Token(Long accountId, String accessToken, String refreshToken) {
		this.accountId = accountId;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
