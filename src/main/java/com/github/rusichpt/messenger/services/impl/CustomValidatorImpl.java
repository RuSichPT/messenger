package com.github.rusichpt.messenger.services.impl;

import com.github.rusichpt.messenger.repositories.UserRepository;
import com.github.rusichpt.messenger.services.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomValidatorImpl implements CustomValidator {
    private final UserRepository repo;

    @Override
    public boolean isUniqueEmail(String email) {
        return repo.findByEmail(email).isEmpty();
    }

    @Override
    public boolean isUniqueUsername(String username) {
        return repo.findByUsername(username).isEmpty();
    }
}
