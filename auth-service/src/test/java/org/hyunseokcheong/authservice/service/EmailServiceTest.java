package org.hyunseokcheong.authservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hyunseokcheong.authservice.dto.EmailRequest;
import org.hyunseokcheong.authservice.entity.Account;
import org.hyunseokcheong.authservice.repository.AccountRepository;
import org.hyunseokcheong.authservice.util.exception.AuthServiceAppException;
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

	@Autowired
	private AccountRepository accountRepository;

	@Test
	void sendEmailTest() {
		String toEmail = "hyunseokcheong@gmail.com";
		EmailRequest request = new EmailRequest(toEmail);
		emailService.sendEmail(request);
	}

	@Test
	void createCertificationCodeTest() {
		String certificationCode = emailService.createCertificationCode();

		Assertions.assertNotNull(certificationCode);
		assertEquals(6, certificationCode.length());
		Assertions.assertTrue(certificationCode.matches("[0-9]+"));
	}

	@Test
	void duplicateEmailTest() {
		String email1 = "hyunseokcheong@gmail.com";
		String password1 = "password";
		accountRepository.save(new Account(email1, password1));

		String email2 = "hyunseokcheong@gmail.com";

		Exception exception = Assertions.assertThrows(AuthServiceAppException.class, () -> {
			emailService.sendEmail(new EmailRequest(email2));
		});
		assertEquals("이미 가입된 이메일입니다.", exception.getMessage());
	}
}
