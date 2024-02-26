package org.hyunseokcheong.authservice.util.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	// BINDING_RESULT
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."),
	// EMAIL
	DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
	EMAIL_AUTH_NOT_FOUND(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
	EMAIL_AUTH_NOT_MATCHED(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다.");

	private HttpStatus httpStatus;
	private String message;
}
