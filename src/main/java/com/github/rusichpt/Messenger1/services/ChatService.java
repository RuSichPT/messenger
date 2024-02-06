package com.github.rusichpt.Messenger1.services;

import com.github.rusichpt.Messenger1.entities.Chat;
import com.github.rusichpt.Messenger1.entities.User;

import java.util.Optional;

public interface ChatService {
    Chat createChat(Chat chat);

    Optional<Chat> findChatByUsers(User user1, User user2);

    Optional<Chat> findSelfChat(User user);

    void deleteChatsByUser(User user);
}
