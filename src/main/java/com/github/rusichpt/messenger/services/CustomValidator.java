package com.github.rusichpt.messenger.services;

public interface CustomValidator {
    boolean isUniqueEmail(String email);

    boolean isUniqueUsername(String username);
}
