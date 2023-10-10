package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    void deleteById(Long id);

    Optional<User> findById(Long id);

    List<User> findAll();
}
