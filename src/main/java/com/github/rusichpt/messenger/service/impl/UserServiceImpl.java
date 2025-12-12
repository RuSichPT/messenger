package com.github.rusichpt.messenger.service.impl;

import com.github.rusichpt.messenger.advice.exceptions.UserExistsException;
import com.github.rusichpt.messenger.dto.UserUpdateDTO;
import com.github.rusichpt.messenger.entity.User;
import com.github.rusichpt.messenger.repository.UserRepository;
import com.github.rusichpt.messenger.service.CustomValidator;
import com.github.rusichpt.messenger.service.EmailService;
import com.github.rusichpt.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final EmailService emailService;
    private final CustomValidator validator;

    @Value("${host.url}")
    private String host;

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
        checkUniqueEmailAndUsername(user.getEmail(), user.getUsername());

        user.setPassword(encoder.encode(user.getPassword()));
        user.setConfirmationCode(UUID.randomUUID().toString());
        User createdUser = userRepo.save(user);

        sendConfirmationCode(createdUser);
        log.info("User created: {}", createdUser);

        return createdUser;
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
    public User updateUser(User user, UserUpdateDTO userUpdateDTO) {
        checkUniqueEmailAndUsername(userUpdateDTO.getEmail(), userUpdateDTO.getUsername());
        String oldEmail = user.getEmail();

        user.setEmail(userUpdateDTO.getEmail());
        user.setUsername(userUpdateDTO.getUsername());
        user.setName(userUpdateDTO.getName());
        user.setSurname(userUpdateDTO.getSurname());

        if (!oldEmail.equals(userUpdateDTO.getEmail())) {
            user.setEmailConfirmed(false);
            user.setConfirmationCode(UUID.randomUUID().toString());
            sendConfirmationCode(user);
        }

        User savedUser = userRepo.save(user);
        log.info("User updated: {}", savedUser);

        return savedUser;
    }

    @Override
    public User updateUser(User user) {
        User savedUser = userRepo.save(user);
        log.info("User updated: {}", savedUser);
        return savedUser;
    }

    @Override
    public User updateUserPass(User user, String password) {
        user.setPassword(encoder.encode(password));
        User updatedUser = userRepo.save(user);
        log.info("User updated password: {}", user);
        return updatedUser;
    }

    @Override
    public void deleteUser(User user) {
        userRepo.deleteById(user.getId());
        log.info("User deleted: {}", user);
    }

    @Override
    public void sendConfirmationCode(User user) {
        String message = String.format("Hello, %s! \n" +
                "Welcome to Messenger. Please, visit next link:" + host + "/api/v1/confirm/%s/%s", user.getUsername(), user.getId(), user.getConfirmationCode());
        emailService.sendSimpleEmail(user.getEmail(), "Confirmation code", message);
    }

    @Override
    public void confirmEmail(Long userId, String code) {
        User user = findUserById(userId);
        if (user.getConfirmationCode().equals(code)) {
            user.setEmailConfirmed(true);
            updateUser(user);
            log.info("User email confirmed");
        }
    }

    private void checkUniqueEmailAndUsername(String email, String username) {
        if (!validator.isUniqueEmail(email))
            throw new UserExistsException(String.format("User with such email: %s exists", email));
        if (!validator.isUniqueUsername(username))
            throw new UserExistsException(String.format("User with such username: %s exists", username));
    }

}
