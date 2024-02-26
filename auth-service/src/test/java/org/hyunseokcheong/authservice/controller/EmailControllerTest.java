package org.hyunseokcheong.authservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hyunseokcheong.authservice.dto.EmailRequest;
import org.hyunseokcheong.authservice.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@WebMvcTest(EmailController.class)
class EmailControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private EmailService emailService;

	@Test
	void sendEmailTest() throws Exception {
		String toEmail = "hyunseokcheong@gmail.com";
		EmailRequest request = new EmailRequest(toEmail);

		mockMvc.perform(post("/email")
				.content(objectMapper.writeValueAsString(request))
				.contentType("application/json"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	void invalidEmailFormTest() throws Exception {
		String toEmail = "hyunseokcheong";
		EmailRequest request = new EmailRequest(toEmail);

		mockMvc.perform(post("/email")
				.content(objectMapper.writeValueAsString(request))
				.contentType("application/json"))
			.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.code").value("INVALID_INPUT_VALUE"),
				jsonPath("$.message").value("입력값이 유효하지 않습니다."),
				jsonPath("$.errors.toEmail").value("이메일 양식이 아닙니다.")
			)
			.andDo(print());
	}

	@Test
	void emptyEmailTest() throws Exception {
		String toEmail = "";
		EmailRequest request = new EmailRequest(toEmail);

		mockMvc.perform(post("/email")
				.content(objectMapper.writeValueAsString(request))
				.contentType("application/json"))
			.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.code").value("INVALID_INPUT_VALUE"),
				jsonPath("$.message").value("입력값이 유효하지 않습니다."),
				jsonPath("$.errors.toEmail").value("이메일을 입력해주세요.")
			)
			.andDo(print());
	}
}
