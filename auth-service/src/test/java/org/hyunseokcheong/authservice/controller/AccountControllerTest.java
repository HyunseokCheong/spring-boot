package org.hyunseokcheong.authservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hyunseokcheong.authservice.dto.AccountRequest;
import org.hyunseokcheong.authservice.entity.Account;
import org.hyunseokcheong.authservice.service.AccountService;
import org.hyunseokcheong.authservice.util.response.SuccessCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

	@Autowired
	private AccountController accountController;

	@MockBean
	private AccountService accountService;

	@Autowired
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(accountController)
			.build();
	}

	@Test
	void joinTest() throws Exception {
		String certificationCode = "123456";
		String email = "hyunseokcheong@gmail.com";
		String password = "Password1234@@";
		AccountRequest request = new AccountRequest(certificationCode, email, password);

		when(accountService.join(any(AccountRequest.class)))
			.thenReturn(new Account(1L, email, password));

		mockMvc.perform(post("/account")
				.content(objectMapper.writeValueAsString(request))
				.contentType("application/json"))
			.andExpectAll(
				status().isCreated(),
				jsonPath("$.code").value(SuccessCode.CREATE_ACCOUNT.toString()),
				jsonPath("$.message").value(SuccessCode.CREATE_ACCOUNT.getMessage()),
				jsonPath("$.data.accountId").value(1L),
				jsonPath("$.data.email").value(email)
			)
			.andDo(print());
	}
}
