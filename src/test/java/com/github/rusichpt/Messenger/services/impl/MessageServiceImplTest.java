package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.models.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MessageServiceImplTest {
    private final MessageServiceImpl service;

    @Autowired
    MessageServiceImplTest(MessageServiceImpl service) {
        this.service = service;
    }

    @Test
    void getMessages() {
        Message message1 = new Message(1L, 2L, "Привет");
        Message message2 = new Message(1L, 2L, "Как дела?");
        service.sendMessage(message1);
        service.sendMessage(message2);

        List<Message> messages = service.getMessages(1L, 2L, 0, 5);

        Assertions.assertEquals(messages.size(), 2);
        Assertions.assertEquals("Привет", messages.get(0).getContent());
    }
}