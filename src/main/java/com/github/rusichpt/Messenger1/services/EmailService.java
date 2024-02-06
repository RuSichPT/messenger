package com.github.rusichpt.Messenger1.services;

public interface EmailService {
    void sendSimpleEmail(String emailTo, String subject, String message);
}
