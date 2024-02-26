package org.hyunseokcheong.authservice.util.exception;

import org.hyunseokcheong.authservice.util.response.ErrorCode;

import lombok.Getter;

@Getter
public class AuthServiceAppException extends RuntimeException {

	private final ErrorCode errorCode;
	private final String message;

	public AuthServiceAppException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
	}

	@Override
	public String toString() {
		return String.format("%s: %s", errorCode, message);
	}
}
