package org.hyunseokcheong.authservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmailServiceTest {

	@Autowired
	private EmailService emailService;

	@Test
	void sendEmailTest() {
		emailService.sendEmail();
	}

	@Test
	void createCertificationCodeTest() {
		String certificationCode = emailService.createCertificationCode();

		Assertions.assertNotNull(certificationCode);
		Assertions.assertEquals(6, certificationCode.length());
		Assertions.assertTrue(certificationCode.matches("[0-9]+"));
	}
}
