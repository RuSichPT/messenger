package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.entities.User;

public interface EmailService {
    void sendSimpleEmail(String emailTo, String subject, String message);

    void sendConfirmationCode(User user);
}
