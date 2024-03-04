package org.hyunseokcheong.authservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hyunseokcheong.authservice.dto.TokenRequest;
import org.hyunseokcheong.authservice.entity.Token;
import org.hyunseokcheong.authservice.service.TokenService;
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
@WebMvcTest(controllers = TokenController.class)
class TokenControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TokenController tokenController;

	@MockBean
	private TokenService tokenService;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(tokenController)
			.build();
	}

	@Test
	void loginTest() throws Exception {
		String email = "hyunseokcheong@gmail.com";
		String password = "Password1234@@";
		TokenRequest request = new TokenRequest(email, password);

		when(tokenService.login(any(TokenRequest.class)))
			.thenReturn(new Token(1L, "accessToken", "refreshToken"));

		mockMvc.perform(post("/token/login")
				.content(objectMapper.writeValueAsString(request))
				.contentType("application/json"))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.code").value(SuccessCode.LOGIN_ACCOUNT.toString()),
				jsonPath("$.message").value(SuccessCode.LOGIN_ACCOUNT.getMessage()),
				jsonPath("$.data.accountId").value(1L),
				jsonPath("$.data.accessToken").value("accessToken")
			)
			.andDo(print());
	}

	@Test
	void reissueTest() throws Exception {
		String accessToken = "accessToken";

		when(tokenService.reissue(accessToken))
			.thenReturn(new Token(1L, "newAccessToken", "newRefreshToken"));

		mockMvc.perform(post("/token/reissue")
				.header("accessToken", accessToken))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.code").value(SuccessCode.REISSUE_ACCOUNT.toString()),
				jsonPath("$.message").value(SuccessCode.REISSUE_ACCOUNT.getMessage()),
				jsonPath("$.data.accountId").value(1L),
				jsonPath("$.data.accessToken").value("newAccessToken")
			)
			.andDo(print());
	}

	@Test
	void logoutTest() throws Exception {
		String accessToken = "accessToken";

		doNothing().when(tokenService).logout(accessToken);

		mockMvc.perform(delete("/token/logout")
				.header("accessToken", accessToken))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.code").value(SuccessCode.LOGOUT_ACCOUNT_THIS_DEVICE.toString()),
				jsonPath("$.message").value(SuccessCode.LOGOUT_ACCOUNT_THIS_DEVICE.getMessage())
			)
			.andDo(print());
	}

	@Test
	void logoutAllTest() throws Exception {
		String accessToken = "accessToken";

		doNothing().when(tokenService).logoutAll(accessToken);

		mockMvc.perform(delete("/token/logout/all")
				.header("accessToken", accessToken))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.code").value(SuccessCode.LOGOUT_ACCOUNT_ALL_DEVICE.toString()),
				jsonPath("$.message").value(SuccessCode.LOGOUT_ACCOUNT_ALL_DEVICE.getMessage())
			)
			.andDo(print());
	}
}

