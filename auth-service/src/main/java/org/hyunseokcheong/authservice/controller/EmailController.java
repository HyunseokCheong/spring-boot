package org.hyunseokcheong.authservice.controller;

import org.hyunseokcheong.authservice.dto.EmailRequest;
import org.hyunseokcheong.authservice.service.EmailService;
import org.hyunseokcheong.authservice.util.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

	private final EmailService emailService;

	@PostMapping
	public ResponseEntity<Object> sendEmail(@RequestBody @Valid EmailRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, bindingResult);
		}
		emailService.sendEmail(request);
		return ApiResponse.success(SuccessCode.SEND_EMAIL);
	}
}
