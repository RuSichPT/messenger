package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.models.Chat;
import com.github.rusichpt.Messenger.models.User;

import java.util.Optional;

public interface ChatService {
    Chat createChat(Chat chat);

    Optional<Chat> findChatByUsers(User user1, User user2);

    Optional<Chat> findSelfChat(User user);

    void deleteChatsByUser(User user);
}
