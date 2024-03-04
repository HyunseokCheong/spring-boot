package org.hyunseokcheong.authservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hyunseokcheong.authservice.dto.EmailAuthRequest;
import org.hyunseokcheong.authservice.entity.EmailAuth;
import org.hyunseokcheong.authservice.service.EmailAuthService;
import org.hyunseokcheong.authservice.util.response.ErrorCode;
import org.hyunseokcheong.authservice.util.response.SuccessCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EmailAuthController.class)
class EmailAuthControllerTest {

	@MockBean
	private EmailAuthService emailAuthService;

	@Autowired
	private EmailAuthController emailAuthController;

	@Autowired
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(emailAuthController)
			.build();
	}

	@Test
	void sendEmailTest() throws Exception {
		String toEmail = "hyunseokcheong@gmail.com";
		EmailAuthRequest request = new EmailAuthRequest(toEmail);

		when(emailAuthService.sendEmail(any(EmailAuthRequest.class)))
			.thenReturn(new EmailAuth(toEmail, "123456"));

		mockMvc.perform(post("/email")
				.content(objectMapper.writeValueAsString(request))
				.contentType("application/json"))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.code").value(SuccessCode.SEND_EMAIL.toString()),
				jsonPath("$.message").value(SuccessCode.SEND_EMAIL.getMessage()),
				jsonPath("$.data.email").value(toEmail),
				jsonPath("$.data.certificationCode").value("123456")
			)
			.andDo(print());
	}

	@Test
	void invalidEmailFormTest() throws Exception {
		String toEmail = "hyunseokcheong";
		EmailAuthRequest request = new EmailAuthRequest(toEmail);

		mockMvc.perform(post("/email")
				.content(objectMapper.writeValueAsString(request))
				.contentType("application/json"))
			.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.code").value(ErrorCode.INVALID_INPUT_VALUE.toString()),
				jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()),
				jsonPath("$.errors.email").value("이메일 양식이 아닙니다.")
			)
			.andDo(print());
	}

	@Test
	void emptyEmailTest() throws Exception {
		String toEmail = "";
		EmailAuthRequest request = new EmailAuthRequest(toEmail);

		mockMvc.perform(post("/email")
				.content(objectMapper.writeValueAsString(request))
				.contentType("application/json"))
			.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.code").value(ErrorCode.INVALID_INPUT_VALUE.toString()),
				jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()),
				jsonPath("$.errors.email").value("이메일을 입력해주세요.")
			)
			.andDo(print());
	}
}
