package com.github.rusichpt.messenger.service;

public interface CustomValidator {
    boolean isUniqueEmail(String email);

    boolean isUniqueUsername(String username);
}
