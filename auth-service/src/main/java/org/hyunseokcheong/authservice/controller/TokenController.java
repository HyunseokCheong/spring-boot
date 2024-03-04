package org.hyunseokcheong.authservice.controller;

import org.hyunseokcheong.authservice.dto.TokenRequest;
import org.hyunseokcheong.authservice.dto.TokenResponse;
import org.hyunseokcheong.authservice.entity.Token;
import org.hyunseokcheong.authservice.service.TokenService;
import org.hyunseokcheong.authservice.util.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

	private final TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid TokenRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, bindingResult);
		}
		Token token = tokenService.login(request);
		TokenResponse response = TokenResponse.of(token);
		return ApiResponse.success(SuccessCode.LOGIN_ACCOUNT, response);
	}

	@PostMapping("/reissue")
	public ResponseEntity<Object> reissue(@RequestHeader("accessToken") String accessToken) {
		Token token = tokenService.reissue(accessToken);
		TokenResponse response = TokenResponse.of(token);
		return ApiResponse.success(SuccessCode.REISSUE_ACCOUNT, response);
	}

	@DeleteMapping("/logout")
	public ResponseEntity<Object> logout(@RequestHeader("accessToken") String accessToken) {
		tokenService.logout(accessToken);
		return ApiResponse.success(SuccessCode.LOGOUT_ACCOUNT_THIS_DEVICE);
	}

	@DeleteMapping("/logout/all")
	public ResponseEntity<Object> logoutAll(@RequestHeader("accessToken") String accessToken) {
		tokenService.logoutAll(accessToken);
		return ApiResponse.success(SuccessCode.LOGOUT_ACCOUNT_ALL_DEVICE);
	}
}
