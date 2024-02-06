package com.github.rusichpt.messenger.services;

public interface EmailService {
    void sendSimpleEmail(String emailTo, String subject, String message);
}
