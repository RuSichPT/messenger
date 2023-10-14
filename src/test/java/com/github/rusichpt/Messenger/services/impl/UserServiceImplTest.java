package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    private final UserServiceImpl service;
    private final PasswordEncoder encoder;
    private final User user = new User(1L, "test@mail.ru", "123",
            "username", "Pavel", "Tokarev", true, UUID.randomUUID().toString());

    @Autowired
    public UserServiceImplTest(UserServiceImpl service, PasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @Test
    void save() {
        User savedUser = service.createUser(user);

        Assertions.assertEquals(user, savedUser);
    }

    @Test
    void checkUniqueFields() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.createUser(user);
            service.createUser(user);
        });
    }

    @Test
    void delete() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.createUser(user);
            service.deleteUserById(user.getId());
            service.findUserById(user.getId());
        });
    }

    @Test
    void updateUserProfileById() {
        UserProfile profile = service.createUser(user).getProfile();
        profile.setUsername("testUsername");
        profile.setSurname("Ivanov");

        UserProfile newProfile = service.updateUserProfileById(user.getId(), profile);

        Assertions.assertEquals(profile, newProfile);
    }

    @Test
    void updateUserPasswordByUsernameNull() {
        service.createUser(user);
        String newPass = "321";

        service.updateUserPasswordById(user.getId(), newPass);
        User foundUser = service.findUserById(user.getId());

        Assertions.assertTrue(encoder.matches(newPass, foundUser.getPassword()));
    }
}