package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.advice.exceptions.UserExistsException;
import com.github.rusichpt.Messenger.dto.UserUpdateDTO;
import com.github.rusichpt.Messenger.entities.User;
import com.github.rusichpt.Messenger.repositories.UserRepository;
import com.github.rusichpt.Messenger.services.CustomValidator;
import com.github.rusichpt.Messenger.services.EmailService;
import com.github.rusichpt.Messenger.services.UserService;
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
    private final EmailService emailService;
    private final CustomValidator validator;

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

        emailService.sendConfirmationCode(createdUser);
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
            emailService.sendConfirmationCode(user);
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

    private void checkUniqueEmailAndUsername(String email, String username) {
        if (!validator.isUniqueEmail(email))
            throw new UserExistsException(String.format("User with such email: %s exists", email));
        if (!validator.isUniqueUsername(username))
            throw new UserExistsException(String.format("User with such username: %s exists", username));
    }

}
