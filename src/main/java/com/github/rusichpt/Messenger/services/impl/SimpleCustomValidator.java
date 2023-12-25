package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.repositories.UserRepository;
import com.github.rusichpt.Messenger.services.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleCustomValidator implements CustomValidator {

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
