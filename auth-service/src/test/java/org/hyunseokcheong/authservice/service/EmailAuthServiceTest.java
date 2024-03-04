package org.hyunseokcheong.authservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hyunseokcheong.authservice.dto.EmailAuthRequest;
import org.hyunseokcheong.authservice.entity.Account;
import org.hyunseokcheong.authservice.entity.EmailAuth;
import org.hyunseokcheong.authservice.repository.AccountRepository;
import org.hyunseokcheong.authservice.util.exception.AuthServiceAppException;
import org.hyunseokcheong.authservice.util.response.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmailAuthServiceTest {

	@Autowired
	private EmailAuthService emailAuthService;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	void sendEmailTest() {
		String toEmail = "hyunseokcheong@gmail.com";
		EmailAuthRequest request = new EmailAuthRequest(toEmail);
		EmailAuth emailAuth = emailAuthService.sendEmail(request);

		assertThat(emailAuth).isNotNull();
		assertEquals(toEmail, emailAuth.getEmail());
		assertEquals(6, emailAuth.getCertificationCode().length());
	}

	@Test
	void createCertificationCodeTest() {
		String certificationCode = emailAuthService.createCertificationCode();

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
			emailAuthService.sendEmail(new EmailAuthRequest(email2));
		});
		assertEquals(ErrorCode.DUPLICATED_EMAIL.getMessage(), exception.getMessage());
	}
}
