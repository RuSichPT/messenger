package com.github.rusichpt.messenger.service.impl;

import com.github.rusichpt.messenger.repository.UserRepository;
import com.github.rusichpt.messenger.service.CustomValidator;
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
