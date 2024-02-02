package com.ssafy.dmobile.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailSender mailSender;

    @Value("${spring.mail.username}")
    private String adminEmail;

    public void sendTemporaryPassword(String toEmail, String memberName, String tempoPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(adminEmail);
        message.setTo(toEmail);
        message.setSubject("[DMobile] 임시 비밀번호 안내 이메일입니다.");
        message.setText(memberName + "님의 임시 비밀번호는 " + tempoPassword + " 입니다.");
        mailSender.send(message);
    }
}
