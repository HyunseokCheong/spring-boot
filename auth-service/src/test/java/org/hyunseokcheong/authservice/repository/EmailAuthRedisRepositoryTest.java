package org.hyunseokcheong.authservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.hyunseokcheong.authservice.entity.EmailAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

@DataRedisTest
class EmailAuthRedisRepositoryTest {

	@Autowired
	private EmailAuthRedisRepository emailAuthRedisRepository;

	@Test
	void saveAndFindEmailAuthTest() {
		String toEmail = "hyunseokcheong@gmail.com";
		String certificationCode = "123456";

		EmailAuth emailAuth = new EmailAuth(toEmail, certificationCode);
		EmailAuth saveAuth = emailAuthRedisRepository.save(emailAuth);

		assertThat(saveAuth).isNotNull();
		assertThat(saveAuth.getEmail()).isEqualTo(toEmail);
		assertThat(saveAuth.getCertificationCode()).isEqualTo(certificationCode);
	}
}
