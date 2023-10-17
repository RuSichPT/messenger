package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.models.User;

public interface EmailService {
    void sendSimpleEmail(String emailTo, String subject, String message);
    void sendConfirmationCode(User user);
}
