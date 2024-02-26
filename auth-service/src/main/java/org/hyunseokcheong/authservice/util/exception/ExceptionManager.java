package org.hyunseokcheong.authservice.util.exception;

import org.hyunseokcheong.authservice.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {

	@ExceptionHandler(AuthServiceAppException.class)
	public ResponseEntity<Object> handlePreOrderAppException(AuthServiceAppException exception) {
		return ApiResponse.error(exception.getErrorCode());
	}
}
