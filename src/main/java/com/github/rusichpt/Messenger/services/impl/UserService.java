package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.models.User;

import java.util.Optional;

public interface UserService {
    User save(User user);

    void deleteById(Long id);

    Optional<User> findById(Long id);
}
