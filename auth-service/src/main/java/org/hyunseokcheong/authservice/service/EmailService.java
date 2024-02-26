package org.hyunseokcheong.authservice.service;

import org.hyunseokcheong.authservice.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {

	@Value("${spring.mail.username}")
	private String fromEmail;

	private final JavaMailSender mailSender;

	public void sendEmail(EmailRequest request) {
		String toEmail = request.toEmail();
		String certificationCode = createCertificationCode();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setSubject("인증 코드 발송");
		message.setText("인증 코드: " + certificationCode);
		mailSender.send(message);
	}

	String createCertificationCode() {
		return String.valueOf((int)(Math.random() * 900000) + 100000);
	}
}
