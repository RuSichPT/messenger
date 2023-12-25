package com.github.rusichpt.Messenger.services;

public interface CustomValidator {
    boolean isUniqueEmail(String email);

    boolean isUniqueUsername(String username);
}
