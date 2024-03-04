package org.hyunseokcheong.authservice.service;

import org.hyunseokcheong.authservice.dto.AccountRequest;
import org.hyunseokcheong.authservice.entity.Account;
import org.hyunseokcheong.authservice.entity.EmailAuth;
import org.hyunseokcheong.authservice.repository.AccountRepository;
import org.hyunseokcheong.authservice.repository.EmailAuthRedisRepository;
import org.hyunseokcheong.authservice.util.exception.AuthServiceAppException;
import org.hyunseokcheong.authservice.util.response.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

	private final AccountRepository accountRepository;
	private final EmailAuthRedisRepository emailRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Account join(AccountRequest request) {
		String email = request.email();
		if (accountRepository.existsByEmail(email)) {
			throw new AuthServiceAppException(ErrorCode.DUPLICATED_EMAIL);
		}

		String certificationCode = request.certificationCode();
		EmailAuth savedEmailAuth = emailRepository.findById(email)
			.orElseThrow(() -> new AuthServiceAppException(ErrorCode.EMAIL_AUTH_NOT_FOUND));
		String savedCertificationCode = savedEmailAuth.getCertificationCode();
		if (!certificationCode.equals(savedCertificationCode)) {
			throw new AuthServiceAppException(ErrorCode.EMAIL_AUTH_NOT_MATCHED);
		}

		String encodedPassword = passwordEncoder.encode(request.password());
		return accountRepository.save(new Account(email, encodedPassword));
	}
}
