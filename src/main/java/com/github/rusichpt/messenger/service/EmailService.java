package com.github.rusichpt.messenger.service;

public interface EmailService {
    void sendSimpleEmail(String emailTo, String subject, String message);
}
