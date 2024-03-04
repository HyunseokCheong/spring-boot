package org.hyunseokcheong.authservice.util.jwt;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String JWT_SECRET;

	@Value("${jwt.access-token-expiration}")
	private int ACCESS_TOKEN_EXPIRATION;

	@Value("${jwt.refresh-token-expiration}")
	private int REFRESH_TOKEN_EXPIRATION;

	public String generateAccessToken(Long accountId) {
		Date now = new Date();

		return JWT.create()
			.withSubject(accountId.toString())
			.withExpiresAt(DateUtils.addMinutes(now, ACCESS_TOKEN_EXPIRATION))
			.withIssuedAt(now)
			.sign(Algorithm.HMAC512(JWT_SECRET));
	}

	public String generateRefreshToken(Long accountId) {
		Date now = new Date();

		return JWT.create()
			.withSubject(accountId.toString())
			.withExpiresAt(DateUtils.addDays(now, REFRESH_TOKEN_EXPIRATION))
			.withIssuedAt(now)
			.sign(Algorithm.HMAC512(JWT_SECRET));
	}

	public boolean isExpired(String token) {
		return JWT.decode(token)
			.getExpiresAt()
			.before(new Date());
	}
}
