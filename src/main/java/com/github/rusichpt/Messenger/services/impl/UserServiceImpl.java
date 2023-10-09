package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Override
    public User save(User user) {
        User user1 = userRepo.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (user1 != null) {
            log.info("Such user: {} exists", user1);
            if (user.getUsername().equals(user1.getUsername()))
                throw new RuntimeException(String.format("User with such username: %s exists", user1.getUsername()));
            if (user.getEmail().equals(user1.getEmail()))
                throw new RuntimeException(String.format("User with such email: %s exists", user1.getEmail()));
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user1 = userRepo.save(user);
        log.info("User: {} is created", user1);

        return user1;
    }

    @Override
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }
}
