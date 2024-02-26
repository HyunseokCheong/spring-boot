package org.hyunseokcheong.authservice.dto;

import org.hyunseokcheong.authservice.entity.EmailAuth;

public record EmailAuthResponse(
	String email,
	String certificationCode
) {

	public static EmailAuthResponse of(EmailAuth emailAuth) {
		return new EmailAuthResponse(emailAuth.getEmail(), emailAuth.getCertificationCode());
	}
}
