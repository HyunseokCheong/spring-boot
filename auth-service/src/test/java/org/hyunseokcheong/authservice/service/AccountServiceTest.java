package org.hyunseokcheong.authservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hyunseokcheong.authservice.dto.AccountRequest;
import org.hyunseokcheong.authservice.dto.EmailAuthRequest;
import org.hyunseokcheong.authservice.entity.Account;
import org.hyunseokcheong.authservice.entity.EmailAuth;
import org.hyunseokcheong.authservice.repository.AccountRepository;
import org.hyunseokcheong.authservice.repository.EmailAuthRedisRepository;
import org.hyunseokcheong.authservice.util.exception.AuthServiceAppException;
import org.hyunseokcheong.authservice.util.response.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountServiceTest {

	@MockBean
	private AccountRepository accountRepository;

	@MockBean
	private EmailAuthRedisRepository emailAuthRepository;

	@Autowired
	private AccountService accountService;

	@MockBean
	private EmailAuthService emailAuthService;

	@Test
	void joinTest() {
		String email = "hyunseokcheong@gmail.com";
		String certificationCode = "123456";
		String password = "password";

		when(emailAuthService.sendEmail(any(EmailAuthRequest.class)))
			.thenReturn(new EmailAuth(email, certificationCode));
		when(emailAuthRepository.findByEmail(any(String.class)))
			.thenReturn(Optional.of(new EmailAuth(email, certificationCode)));
		when(accountRepository.save(any(Account.class)))
			.thenReturn(new Account(email, password));

		Account joinAccount = accountService.join(new AccountRequest(certificationCode, email, password));

		assertThat(joinAccount).isNotNull();
		assertThat(joinAccount.getEmail()).isEqualTo(email);
		assertThat(joinAccount.getPassword()).isEqualTo(password);
	}

	@Test
	void joinDuplicatedEmailTest() {
		String email = "hyunseokcheong@gmail.com";
		String certificationCode = "123456";
		String password = "password";
		AccountRequest request = new AccountRequest(certificationCode, email, password);

		when(accountRepository.existsByEmail(email))
			.thenReturn(true);

		Exception exception = assertThrows(AuthServiceAppException.class, () -> {
			accountService.join(request);
		});
		assertEquals(ErrorCode.DUPLICATED_EMAIL.getMessage(), exception.getMessage());
	}

	@Test
	void joinEmailAuthNotFoundTest() {
		String email = "hyunseokcheong@gmail.com";
		String certificationCode = "123456";
		String password = "password";
		AccountRequest request = new AccountRequest(certificationCode, email, password);

		when(emailAuthRepository.findByEmail(email))
			.thenReturn(Optional.empty());

		Exception exception = assertThrows(AuthServiceAppException.class, () -> {
			accountService.join(request);
		});
		assertEquals(ErrorCode.EMAIL_AUTH_NOT_FOUND.getMessage(), exception.getMessage());
	}

	@Test
	void joinEmailAuthNotMatched() {
		String email = "hyunseokcheong@gmail.com";
		String certificationCode = "123456";
		String password = "password";
		AccountRequest request = new AccountRequest(certificationCode, email, password);

		String saveCertificationCode = "654321";
		when(emailAuthRepository.findByEmail(email))
			.thenReturn(Optional.of(new EmailAuth(email, saveCertificationCode)));

		Exception exception = assertThrows(AuthServiceAppException.class, () -> {
			accountService.join(request);
		});
		assertEquals(ErrorCode.EMAIL_AUTH_NOT_MATCHED.getMessage(), exception.getMessage());
	}
}
