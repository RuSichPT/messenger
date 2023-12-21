package com.github.rusichpt.Messenger.configs;

import com.github.rusichpt.Messenger.advice.exceptions.UserExistsException;
import com.github.rusichpt.Messenger.entities.Chat;
import com.github.rusichpt.Messenger.entities.Message;
import com.github.rusichpt.Messenger.entities.User;
import com.github.rusichpt.Messenger.services.ChatService;
import com.github.rusichpt.Messenger.services.MessageService;
import com.github.rusichpt.Messenger.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
@Slf4j
public class WebConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner dataLoader(UserService userService, ChatService chatService,
                                        MessageService messageService) {
        return args -> {
            try {
                User user1 = new User(null, "user1@mail.ru", "123",
                        "user1", "Pavel", "Tokarev",
                        true, UUID.randomUUID().toString());
                User user2 = new User(null, "user2@mail.ru", "321",
                        "user2", "Alex", "Firov",
                        true, UUID.randomUUID().toString());
                userService.createUser(user1);
                userService.createUser(user2);

                Chat chat = new Chat(user1, user2);
                chatService.createChat(chat);

                Message message1 = new Message(user1, chat, "Привет user2");
                Thread.sleep(1);
                messageService.sendMessage(message1);

                Message message2 = new Message(user2, chat, "Привет user1, как дела?");
                Thread.sleep(1);
                messageService.sendMessage(message2);

                Message message3 = new Message(user1, chat, "Нормально, ты как?");
                Thread.sleep(1);
                messageService.sendMessage(message3);

                Message message4 = new Message(user2, chat, "Хорошо!");
                Thread.sleep(1);
                messageService.sendMessage(message4);

            } catch (UserExistsException e) {
                log.info(e.getMessage());
            }
            log.info("DataLoader loaded data");
        };
    }
}