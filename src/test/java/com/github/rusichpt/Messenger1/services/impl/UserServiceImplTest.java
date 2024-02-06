package com.github.rusichpt.Messenger1.services.impl;

import com.github.rusichpt.Messenger1.dto.UserUpdateDTO;
import com.github.rusichpt.Messenger1.entities.User;
import com.github.rusichpt.Messenger1.services.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    private final UserServiceImpl service;
    private final PasswordEncoder encoder;

    @MockBean // заменяем реальный сервис, чтобы не спамить почту на несуществующий адрес.
    private final EmailService emailService;

    private final User user = new User(null, "test@mail.ru", "123",
            "username", "Pavel", "Tokarev", true, UUID.randomUUID().toString());

    @Autowired
    public UserServiceImplTest(UserServiceImpl service, PasswordEncoder encoder, ModelMapper mapper, EmailService emailService) {
        this.service = service;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    @Test
    void save() {
        User createdUser = service.createUser(user);
        user.setId(createdUser.getId());

        Assertions.assertEquals(user, createdUser);
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
            User createdUser = service.createUser(user);
            service.deleteUser(createdUser);
            service.findUserById(createdUser.getId());
        });
    }

    @Test
    void updateUser() {
        User createdUser = service.createUser(user);
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .username("username1")
                .email("test1@mail.ru")
                .name("testUsername")
                .surname("Ivanov")
                .build();

        User savedUser = service.updateUser(createdUser, userUpdateDTO);

        Assertions.assertEquals(savedUser.getName(), userUpdateDTO.getName());
    }

    @Test
    void updateUserPassword() {
        User savedUser = service.createUser(user);
        String newPass = "321";

        service.updateUserPass(savedUser, newPass);
        User foundUser = service.findUserById(savedUser.getId());

        Assertions.assertTrue(encoder.matches(newPass, foundUser.getPassword()));
    }
}