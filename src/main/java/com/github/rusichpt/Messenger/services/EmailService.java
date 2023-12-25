package com.github.rusichpt.Messenger.services;

public interface EmailService {
    void sendSimpleEmail(String emailTo, String subject, String message);
}
