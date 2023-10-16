package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.models.Chat;
import com.github.rusichpt.Messenger.models.Message;
import com.github.rusichpt.Messenger.models.User;
import com.github.rusichpt.Messenger.services.ChatService;
import com.github.rusichpt.Messenger.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class MessageServiceImplTest {
    private final MessageServiceImpl messageService;
    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    MessageServiceImplTest(MessageServiceImpl messageService, ChatServiceImpl chatService, UserService userService) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.userService = userService;
    }

    @Test
    void getMessages() {
        User user1 = userService.findUserByUsername("user1");
        User user2 = userService.findUserByUsername("user2");
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