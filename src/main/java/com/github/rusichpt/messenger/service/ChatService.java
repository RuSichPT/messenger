package com.github.rusichpt.messenger.service;

import com.github.rusichpt.messenger.entity.Chat;
import com.github.rusichpt.messenger.entity.User;

import java.util.Optional;

public interface ChatService {
    Chat createChat(Chat chat);

    Optional<Chat> findChatByUsers(User user1, User user2);

    Optional<Chat> findSelfChat(User user);

    void deleteChatsByUser(User user);
}
