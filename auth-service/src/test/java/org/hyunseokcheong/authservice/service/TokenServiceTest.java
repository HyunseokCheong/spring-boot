package org.hyunseokcheong.authservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hyunseokcheong.authservice.dto.TokenRequest;
import org.hyunseokcheong.authservice.entity.Account;
import org.hyunseokcheong.authservice.entity.Token;
import org.hyunseokcheong.authservice.repository.AccountRepository;
import org.hyunseokcheong.authservice.repository.TokenRedisRepository;
import org.hyunseokcheong.authservice.util.jwt.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TokenServiceTest {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JWTUtil jwtUtil;

	@MockBean
	private TokenRedisRepository tokenRedisRepository;

	@MockBean
	private AccountRepository accountRepository;

	@BeforeEach
	void setUp() {
		accountRepository.deleteAll();
		tokenRedisRepository.deleteAll();
	}

	@Test
	void loginTest() {
		String email = "hyunseokcheong@gmail.com";
		String password = "Password1234@@";
		TokenRequest request = new TokenRequest(email, password);

		when(accountRepository.findByEmail(email))
			.thenReturn(Optional.of(new Account(1L, email, passwordEncoder.encode(password))));

		Token token = tokenService.login(request);

		assertThat(token).isNotNull();
		assertThat(token.getAccountId()).isEqualTo(1L);
		assertThat(token.getAccessToken()).isNotNull();
		assertThat(token.getRefreshToken()).isNotNull();
	}

	@Test
	void reissueTest() {
		String accessToken = jwtUtil.generateAccessToken(1L);
		String refreshToken = jwtUtil.generateRefreshToken(1L);

		when(tokenRedisRepository.findByAccessToken(accessToken))
			.thenReturn(Optional.of(new Token(1L, accessToken, refreshToken)));

		Token token = tokenService.reissue(accessToken);

		assertThat(token).isNotNull();
		assertThat(token.getAccountId()).isEqualTo(1L);
		assertThat(token.getAccessToken()).isNotNull();
		assertThat(token.getRefreshToken()).isNotNull();
	}

	@Test
	void logoutTest() {
		String accessToken = jwtUtil.generateAccessToken(1L);
		String refreshToken = jwtUtil.generateRefreshToken(1L);

		when(tokenRedisRepository.findByAccessToken(accessToken))
			.thenReturn(Optional.of(new Token(1L, accessToken, refreshToken)));

		tokenService.logout(accessToken);
	}

	@Test
	void logoutAllTest() {
		Long accountId = 1L;
		String email = "hyunseokcheong@gmail.com";
		String password = "Password1234@@";
		String accessToken = jwtUtil.generateAccessToken(1L);
		String refreshToken = jwtUtil.generateRefreshToken(1L);

		when(tokenRedisRepository.findByAccessToken(accessToken))
			.thenReturn(Optional.of(new Token(1L, accessToken, refreshToken)));
		when(accountRepository.findById(accountId))
			.thenReturn(Optional.of(new Account(1L, email, passwordEncoder.encode(password))));
		when(tokenRedisRepository.findAllByAccountId(accountId))
			.thenReturn(java.util.List.of(new Token(1L, accessToken, refreshToken)));

		tokenService.logoutAll(accessToken);
	}
}
