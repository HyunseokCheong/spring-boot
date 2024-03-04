package org.hyunseokcheong.authservice.util.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessCode {
	// EMAIL
	SEND_EMAIL(HttpStatus.OK, "이메일 전송에 성공했습니다."),
	// ACCOUNT
	CREATE_ACCOUNT(HttpStatus.CREATED, "계정 생성에 성공했습니다."),
	// TOKEN
	LOGIN_ACCOUNT(HttpStatus.OK, "로그인에 성공했습니다."),
	REISSUE_ACCOUNT(HttpStatus.OK, "토큰 재발급에 성공했습니다."),
	LOGOUT_ACCOUNT_THIS_DEVICE(HttpStatus.OK, "로그아웃에 성공했습니다."),
	LOGOUT_ACCOUNT_ALL_DEVICE(HttpStatus.OK, "모든 디바이스에서 로그아웃에 성공했습니다.");

	private HttpStatus httpStatus;
	private String message;
}
