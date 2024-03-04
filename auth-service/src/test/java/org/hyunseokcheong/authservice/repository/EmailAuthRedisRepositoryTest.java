package org.hyunseokcheong.authservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.hyunseokcheong.authservice.entity.EmailAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

@DataRedisTest
class EmailAuthRedisRepositoryTest {

	@Autowired
	private EmailAuthRedisRepository emailAuthRedisRepository;

	@BeforeEach
	void setUp() {
		emailAuthRedisRepository.deleteAll();
	}

	@Test
	void saveAndFindEmailAuthTest() {
		String toEmail = "hyunseokcheong@gmail.com";
		String certificationCode = "123456";
		EmailAuth emailAuth = new EmailAuth(toEmail, certificationCode);
		emailAuthRedisRepository.save(emailAuth);

		EmailAuth findAuth = emailAuthRedisRepository.findById(toEmail).orElse(null);

		assertThat(findAuth).isNotNull();
		assertThat(findAuth.getEmail()).isEqualTo(toEmail);
		assertThat(findAuth.getCertificationCode()).isEqualTo(certificationCode);
	}
}
