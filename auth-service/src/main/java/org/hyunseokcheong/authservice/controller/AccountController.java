package org.hyunseokcheong.authservice.controller;

import org.hyunseokcheong.authservice.dto.AccountRequest;
import org.hyunseokcheong.authservice.dto.AccountResponse;
import org.hyunseokcheong.authservice.entity.Account;
import org.hyunseokcheong.authservice.service.AccountService;
import org.hyunseokcheong.authservice.util.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

	private final AccountService accountService;

	@PostMapping
	public ResponseEntity<Object> join(@RequestBody @Valid AccountRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, bindingResult);
		}
		Account account = accountService.join(request);
		return ApiResponse.success(SuccessCode.CREATE_ACCOUNT, AccountResponse.of(account));
	}
}
