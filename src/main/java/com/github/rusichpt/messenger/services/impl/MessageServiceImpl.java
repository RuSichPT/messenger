package com.github.rusichpt.messenger.services.impl;

import com.github.rusichpt.messenger.entities.Chat;
import com.github.rusichpt.messenger.entities.Message;
import com.github.rusichpt.messenger.entities.User;
import com.github.rusichpt.messenger.repositories.MessageRepository;
import com.github.rusichpt.messenger.services.ChatService;
import com.github.rusichpt.messenger.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepo;

    private final ChatService chatService;

    @Override
    public void sendMessage(User from, User to, String content) {
        Optional<Chat> optChat;
        if (from.getUsername().equals(to.getUsername())) {
            optChat = chatService.findSelfChat(from);
        } else {
            optChat = chatService.findChatByUsers(from, to);
        }

        Chat chat = optChat.orElseGet(() -> chatService.createChat(new Chat(from, to)));
        messageRepo.save(new Message(from, chat, content));
    }

    @Override
    public List<Message> getMessages(User user1, User user2, int pageNumber, int pageSize) {
        Optional<Chat> optChat = chatService.findChatByUsers(user1, user2);
        if (optChat.isPresent()) {
            Chat chat = optChat.get();
            return findMessagesByChat(chat, pageNumber, pageSize);
        } else {
            return new ArrayList<>();
        }
    }

    private List<Message> findMessagesByChat(Chat chat, int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("date").ascending());
        Page<Message> page = messageRepo.findAllByChat(chat, pageRequest);
        return page.getContent();
    }
}
