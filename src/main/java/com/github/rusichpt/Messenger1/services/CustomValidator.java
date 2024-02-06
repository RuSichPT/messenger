package com.github.rusichpt.Messenger1.services;

public interface CustomValidator {
    boolean isUniqueEmail(String email);

    boolean isUniqueUsername(String username);
}
