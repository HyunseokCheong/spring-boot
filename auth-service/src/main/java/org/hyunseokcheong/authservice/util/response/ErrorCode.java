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
	DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");

	private HttpStatus httpStatus;
	private String message;
}
