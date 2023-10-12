package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.dto.UserProfile;
import com.github.rusichpt.Messenger.models.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    private final UserServiceImpl service;

    private final User user = new User(1L, "test@mail.ru", "123",
            "username", "Pavel", "Tokarev");

    @Autowired
    UserServiceImplTest(UserServiceImpl service) {
        this.service = service;
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
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            service.createUser(user);
            service.updateUserPasswordById(user.getId(), null);
        });
    }
}