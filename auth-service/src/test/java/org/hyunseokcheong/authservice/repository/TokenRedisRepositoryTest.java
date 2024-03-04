package org.hyunseokcheong.authservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hyunseokcheong.authservice.entity.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

@DataRedisTest
class TokenRedisRepositoryTest {

	@Autowired
	private TokenRedisRepository tokenRedisRepository;

	@BeforeEach
	void setUp() {
		tokenRedisRepository.deleteAll();
	}

	@Test
	void findByAccessTokenTest() {
		Long accountId = 1L;
		String accessToken = "access-token";
		String refreshToken = "refresh-token";
		Token token = new Token(accountId, accessToken, refreshToken);
		tokenRedisRepository.save(token);

		Token findToken = tokenRedisRepository.findByAccessToken(accessToken).orElse(null);

		assertThat(findToken).isNotNull();
		assertThat(findToken.getAccountId()).isEqualTo(accountId);
		assertThat(findToken.getAccessToken()).isEqualTo(accessToken);
	}

	@Test
	void findAllByAccountIdTest() {
		Long accountId = 1L;
		String accessToken = "access-token";
		String refreshToken = "refresh-token";
		for (int i = 0; i < 5; i++) {
			Token token = new Token(accountId, accessToken + i, refreshToken + i);
			tokenRedisRepository.save(token);
		}

		List<Token> tokenList = tokenRedisRepository.findAllByAccountId(accountId);

		assertThat(tokenList.size()).isEqualTo(5);
		for (Token token : tokenList) {
			assertThat(token.getAccountId()).isEqualTo(accountId);
		}
	}
}
