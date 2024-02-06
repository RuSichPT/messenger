package com.github.rusichpt.Messenger1.services.impl;

import com.github.rusichpt.Messenger1.entities.Chat;
import com.github.rusichpt.Messenger1.entities.User;
import com.github.rusichpt.Messenger1.repositories.ChatRepository;
import com.github.rusichpt.Messenger1.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepo;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepo.save(chat);
    }

    @Override
    public Optional<Chat> findChatByUsers(User user1, User user2) {
        return chatRepo.findByUsers(user1, user2);
    }

    @Override
    public Optional<Chat> findSelfChat(User user) {
        return chatRepo.findSelfChat(user);
    }

    @Override
    public void deleteChatsByUser(User user) {
        chatRepo.deleteByUser1OrUser2(user, user);
    }
}
