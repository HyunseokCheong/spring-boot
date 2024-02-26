package org.hyunseokcheong.authservice.util.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ApiResponse {

	public record SuccessResponse(String code, String message) {
	}

	public record SuccessDataResponse(String code, String message, Object data) {
	}

	public record ErrorResponse(String code, String message) {
	}

	public record ErrorDataResponse(String code, String message, Map<String, String> errors) {
	}

	public static ResponseEntity<Object> success(SuccessCode successCode) {
		return ResponseEntity.status(successCode.getHttpStatus())
			.body(new SuccessResponse(successCode.toString(), successCode.getMessage()));
	}

	public static ResponseEntity<Object> success(SuccessCode successCode, Object data) {
		return ResponseEntity.status(successCode.getHttpStatus())
			.body(new SuccessDataResponse(successCode.toString(), successCode.getMessage(), data));
	}

	public static ResponseEntity<Object> error(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.toString(), errorCode.getMessage()));
	}

	public static ResponseEntity<Object> error(ErrorCode errorCode, BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(new ErrorDataResponse(errorCode.toString(), errorCode.getMessage(), errors));
	}
}
