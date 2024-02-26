package org.hyunseokcheong.authservice.service;

import org.hyunseokcheong.authservice.dto.AccountRequest;
import org.hyunseokcheong.authservice.entity.Account;
import org.hyunseokcheong.authservice.entity.EmailAuth;
import org.hyunseokcheong.authservice.repository.AccountRepository;
import org.hyunseokcheong.authservice.repository.EmailAuthRedisRepository;
import org.hyunseokcheong.authservice.util.exception.AuthServiceAppException;
import org.hyunseokcheong.authservice.util.response.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

	private final AccountRepository accountRepository;
	private final EmailAuthRedisRepository emailRepository;

	@Transactional
	public Account join(AccountRequest request) {
		String inputEmail = request.email();
		if (accountRepository.existsByEmail(inputEmail)) {
			throw new AuthServiceAppException(ErrorCode.DUPLICATED_EMAIL);
		}

		String inputCertificationCode = request.certificationCode();
		EmailAuth savedEmailAuth = emailRepository.findByEmail(inputEmail)
			.orElseThrow(() -> new AuthServiceAppException(ErrorCode.EMAIL_AUTH_NOT_FOUND));
		String savedCertificationCode = savedEmailAuth.getCertificationCode();
		if (!inputCertificationCode.equals(savedCertificationCode)) {
			throw new AuthServiceAppException(ErrorCode.EMAIL_AUTH_NOT_MATCHED);
		}

		// Todo: encrypt password
		String inputPassword = request.password();
		return accountRepository.save(new Account(inputEmail, inputPassword));
	}
}
