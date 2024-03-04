package org.hyunseokcheong.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailAuthRequest(
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "이메일 양식이 아닙니다.")
	String email
) {
}
