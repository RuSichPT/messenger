package com.github.rusichpt.messenger.config;

import com.github.rusichpt.messenger.advice.exceptions.UserExistsException;
import com.github.rusichpt.messenger.entity.User;
import com.github.rusichpt.messenger.service.ChatService;
import com.github.rusichpt.messenger.service.MessageService;
import com.github.rusichpt.messenger.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

                Thread.sleep(1);
                messageService.sendMessage(user1, user2, "Привет user2");
                Thread.sleep(1);
                messageService.sendMessage(user2, user1, "Привет user1, как дела?");
                Thread.sleep(1);
                messageService.sendMessage(user1, user2, "Нормально, ты как?");
                Thread.sleep(1);
                messageService.sendMessage(user2, user1, "Хорошо!");

            } catch (UserExistsException e) {
                log.info(e.getMessage());
            }
            log.info("DataLoader loaded data");
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}