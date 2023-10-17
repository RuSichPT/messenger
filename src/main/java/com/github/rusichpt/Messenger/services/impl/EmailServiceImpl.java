package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${host.url}")
    private String host;

    @Override
    public void sendSimpleEmail(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    @Override
    public void sendConfirmationCode(User user) {
        String message = String.format("Hello, %s! \n" +
                "Welcome to Messenger. Please, visit next link:" + host + "/confirm/%s/%s", user.getUsername(), user.getId(), user.getConfirmationCode());
        sendSimpleEmail(user.getEmail(), "Confirmation code", message);
    }
}
