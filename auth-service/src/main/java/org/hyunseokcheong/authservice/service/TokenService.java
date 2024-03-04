package org.hyunseokcheong.authservice.service;

import java.util.List;

import org.hyunseokcheong.authservice.dto.TokenRequest;
import org.hyunseokcheong.authservice.entity.Account;
import org.hyunseokcheong.authservice.entity.Token;
import org.hyunseokcheong.authservice.repository.AccountRepository;
import org.hyunseokcheong.authservice.repository.TokenRedisRepository;
import org.hyunseokcheong.authservice.util.exception.AuthServiceAppException;
import org.hyunseokcheong.authservice.util.jwt.JWTUtil;
import org.hyunseokcheong.authservice.util.response.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

	private final AccountRepository accountRepository;
	private final TokenRedisRepository tokenRedisRepository;
	private final PasswordEncoder passwordEncoder;
	private final JWTUtil jwtUtil;

	@Transactional
	public Token login(TokenRequest request) {
		String email = request.email();
		String password = request.password();

		Account account = accountRepository.findByEmail(email)
			.orElseThrow(() -> new AuthServiceAppException(ErrorCode.ACCOUNT_NOT_FOUND));
		if (!passwordEncoder.matches(password, account.getPassword())) {
			throw new AuthServiceAppException(ErrorCode.INVALID_PASSWORD);
		}

		long accountId = account.getAccountId();
		String accessToken = jwtUtil.generateAccessToken(accountId);
		String refreshToken = jwtUtil.generateRefreshToken(accountId);
		Token token = new Token(accountId, accessToken, refreshToken);
		tokenRedisRepository.save(token);
		return token;
	}

	@Transactional
	public Token reissue(String accessToken) {
		Token token = tokenRedisRepository.findByAccessToken(accessToken)
			.orElseThrow(() -> new AuthServiceAppException(ErrorCode.TOKEN_NOT_FOUND));
		if (jwtUtil.isExpired(token.getRefreshToken())) {
			throw new AuthServiceAppException(ErrorCode.EXPIRED_TOKEN);
		}
		tokenRedisRepository.delete(token);

		Long accountId = token.getAccountId();
		String newAccessToken = jwtUtil.generateAccessToken(accountId);
		String newRefreshToken = jwtUtil.generateRefreshToken(accountId);
		Token newToken = new Token(accountId, newAccessToken, newRefreshToken);
		tokenRedisRepository.save(newToken);
		return newToken;
	}

	@Transactional
	public void logout(String accessToken) {
		Token token = tokenRedisRepository.findByAccessToken(accessToken)
			.orElseThrow(() -> new AuthServiceAppException(ErrorCode.TOKEN_NOT_FOUND));
		tokenRedisRepository.delete(token);
	}

	@Transactional
	public void logoutAll(String accessToken) {
		Token token = tokenRedisRepository.findByAccessToken(accessToken)
			.orElseThrow(() -> new AuthServiceAppException(ErrorCode.TOKEN_NOT_FOUND));
		Long accountId = token.getAccountId();
		if (accountRepository.findById(accountId).isEmpty()) {
			throw new AuthServiceAppException(ErrorCode.ACCOUNT_NOT_FOUND);
		}
		List<Token> tokenList = tokenRedisRepository.findAllByAccountId(accountId);
		if (tokenList.isEmpty()) {
			throw new AuthServiceAppException(ErrorCode.TOKEN_NOT_FOUND);
		}
		tokenRedisRepository.deleteAll(tokenList);
	}
}
