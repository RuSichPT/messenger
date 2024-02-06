package com.github.rusichpt.messenger.services;

import com.github.rusichpt.messenger.entities.Chat;
import com.github.rusichpt.messenger.entities.User;

import java.util.Optional;

public interface ChatService {
    Chat createChat(Chat chat);

    Optional<Chat> findChatByUsers(User user1, User user2);

    Optional<Chat> findSelfChat(User user);

    void deleteChatsByUser(User user);
}
