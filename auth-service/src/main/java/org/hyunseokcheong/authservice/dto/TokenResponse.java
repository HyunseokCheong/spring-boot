package org.hyunseokcheong.authservice.dto;

import org.hyunseokcheong.authservice.entity.Token;

public record TokenResponse(
	Long accountId,
	String accessToken
) {

	public static TokenResponse of(Token token) {
		return new TokenResponse(token.getAccountId(), token.getAccessToken());
	}
}
