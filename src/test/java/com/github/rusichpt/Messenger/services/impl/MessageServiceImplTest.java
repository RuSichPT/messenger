package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.models.Chat;
import com.github.rusichpt.Messenger.models.Message;
import com.github.rusichpt.Messenger.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class MessageServiceImplTest {
    private final MessageServiceImpl messageService;
    private final ChatServiceImpl chatService;

    private final User user1 = new User(1L, "user1@mail.ru", "123",
            "user1", "Pavel", "Tokarev", true, UUID.randomUUID().toString());
    private final User user2 = new User(2L, "user2@mail.ru", "321",
            "user2", "Alex", "Firov", true, UUID.randomUUID().toString());

    @Autowired
    MessageServiceImplTest(MessageServiceImpl messageService, ChatServiceImpl chatService) {
        this.messageService = messageService;
        this.chatService = chatService;
    }

    @Test
    void getMessages() {
        Chat chat = chatService.createChat(new Chat(user1, user2));
        messageService.sendMessage(new Message(user1, chat, "Привет"));
        messageService.sendMessage(new Message(user1, chat, "Как дела?"));
        messageService.sendMessage(new Message(user2, chat, "Нормально"));
        messageService.sendMessage(new Message(user2, chat, "Ты как?"));

        List<Message> messages = messageService.getMessages(chat, 0, 5);

        Assertions.assertEquals(messages.size(), 4);
        Assertions.assertEquals("Привет", messages.get(0).getContent());
        Assertions.assertEquals("Ты как?", messages.get(3).getContent());
    }
}