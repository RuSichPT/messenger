package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.advice.exceptions.UserExistsException;
import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.repositories.UserRepository;
import com.github.rusichpt.Messenger.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Optional<User> optUser = userRepo.findById(id);
        return optUser
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepo.findByUsername(username);
        return optUser
                .orElseThrow(() -> new UsernameNotFoundException("User ‘" + username + "’ not found"));
    }

    @Override
    public User createUser(User user) {
        User foundUser = userRepo.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (foundUser != null) {
            log.info("Such user: {} exists", foundUser);
            if (user.getUsername().equals(foundUser.getUsername()))
                throw new UserExistsException(String.format("User with such username: %s exists", foundUser.getUsername()));
            if (user.getEmail().equals(foundUser.getEmail()))
                throw new UserExistsException(String.format("User with such email: %s exists", foundUser.getEmail()));
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setConfirmationCode(UUID.randomUUID().toString());
        foundUser = userRepo.save(user);
        log.info("User created: {}", foundUser);

        return foundUser;
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
    public User updateUser(User user) {
        return userRepo.save(user);
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
    public void updateUserPasswordById(Long id, String password) {
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

    @PostConstruct
    private void postConstruct() {
        try {
            User user1 = new User(null, "user1@mail.ru", "123",
                    "user1", "Pavel", "Tokarev", true, UUID.randomUUID().toString());
            User user2 = new User(null, "user2@mail.ru", "321",
                    "user2", "Alex", "Firov", true, UUID.randomUUID().toString());

            createUser(user1);
            createUser(user2);
        } catch (UserExistsException e) {
            log.info(e.getMessage());
        }
    }

}
