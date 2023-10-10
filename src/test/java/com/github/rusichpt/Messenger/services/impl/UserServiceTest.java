package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    private final UserServiceImpl service;

    private final User user = new User(1L, "test@mail.ru", "123",
            "username", "Pavel", "Tokarev");

    @Autowired
    UserServiceTest(UserServiceImpl service) {
        this.service = service;
    }

    @Test
    void correctlySave() {
        User savedUser = service.save(user);
        Assertions.assertEquals(user, savedUser);
    }

    @Test
    void checkUniqueFields() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.save(user);
            service.save(user);
        });
    }

    @Test
    void correctlyDeleteById() {
        service.save(user);
        service.deleteById(user.getId());
        Assertions.assertFalse(service.findById(user.getId()).isPresent());
    }
}