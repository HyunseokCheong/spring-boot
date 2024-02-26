package org.hyunseokcheong.authservice.util.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessCode {
	// EMAIL
	SEND_EMAIL(HttpStatus.OK, "이메일 전송에 성공했습니다.");

	private HttpStatus httpStatus;
	private String message;
}
