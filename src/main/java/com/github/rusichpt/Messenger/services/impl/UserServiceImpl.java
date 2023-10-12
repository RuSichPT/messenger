package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.repositories.UserRepository;
import com.github.rusichpt.Messenger.services.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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
    public User findUserById(Long id) {
        return userRepo.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User ‘" + username + "’ not found"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserProfile updateUserProfileById(Long id, UserProfile profile) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User ‘" + profile.getUsername() + "’ not found"));
        user.setProfile(profile);
        UserProfile newProfile = userRepo.save(user).getProfile();
        log.info("User with id: {} updated profile: {}", id, newProfile);
        return newProfile;
    }

    @Override
    public void updateUserPasswordById(Long id, @NotNull String password) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
        log.info("User with id: {} updated password", id);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
        log.info("User with id: {} deleted", id);
    }


}
