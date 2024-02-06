package com.github.rusichpt.messenger.services.impl;

import com.github.rusichpt.messenger.entities.Message;
import com.github.rusichpt.messenger.entities.User;
import com.github.rusichpt.messenger.services.EmailService;
import com.github.rusichpt.messenger.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class MessageServiceImplTest {
    private final MessageServiceImpl messageService;
    @MockBean // заменяем реальный сервис, чтобы не спамить почту на несуществующий адрес.
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    MessageServiceImplTest(MessageServiceImpl messageService, EmailService emailService, UserService userService) {
        this.messageService = messageService;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Test
    void getMessages() {
        User user1 = userService.findUserByUsername("user1");
        User user2 = userService.findUserByUsername("user2");

        List<Message> messages = messageService.getMessages(user1, user2, 0, 5);

        Assertions.assertEquals(4, messages.size());
        Assertions.assertEquals("Привет user2", messages.get(0).getContent());
        Assertions.assertEquals("Хорошо!", messages.get(3).getContent());
    }
}