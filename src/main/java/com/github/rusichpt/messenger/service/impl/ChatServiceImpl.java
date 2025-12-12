package com.github.rusichpt.messenger.service.impl;

import com.github.rusichpt.messenger.entity.Chat;
import com.github.rusichpt.messenger.entity.User;
import com.github.rusichpt.messenger.repository.ChatRepository;
import com.github.rusichpt.messenger.service.ChatService;
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
