package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.repositories.UserRepository;
import com.github.rusichpt.Messenger.services.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Override
    public User createUser(User user) {
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
        log.info("User: {} created", user1);

        return user1;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserProfile updateUserProfileByUsername(String username, UserProfile profile) {
        User user = userRepo.findByUsername(username).orElseThrow();
        user.setProfile(profile);
        UserProfile newProfile = userRepo.save(user).getProfile();
        log.info("User: {} updated profile: {}", username, newProfile);
        return newProfile;
    }

    @Override
    public void updateUserPasswordByUsername(String username, @NotNull String password) {
        User user = userRepo.findByUsername(username).orElseThrow();
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
        log.info("User: {} updated password", username);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepo.deleteByUsername(username);
        log.info("User with id: {} deleted", username);
    }


}
