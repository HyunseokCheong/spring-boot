package org.hyunseokcheong.authservice.controller;

import org.hyunseokcheong.authservice.dto.EmailAuthRequest;
import org.hyunseokcheong.authservice.dto.EmailAuthResponse;
import org.hyunseokcheong.authservice.entity.EmailAuth;
import org.hyunseokcheong.authservice.service.EmailAuthService;
import org.hyunseokcheong.authservice.util.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailAuthController {

	private final EmailAuthService emailAuthService;

	@PostMapping
	public ResponseEntity<Object> sendEmail(@RequestBody @Valid EmailAuthRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, bindingResult);
		}
		EmailAuth emailAuth = emailAuthService.sendEmail(request);
		return ApiResponse.success(SuccessCode.SEND_EMAIL, EmailAuthResponse.of(emailAuth));
	}
}
