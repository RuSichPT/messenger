package com.github.rusichpt.Messenger.services.impl;

import com.github.rusichpt.Messenger.models.Chat;
import com.github.rusichpt.Messenger.models.Message;
import com.github.rusichpt.Messenger.repositories.MessageRepository;
import com.github.rusichpt.Messenger.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepo;

    @Override
    public void sendMessage(Message message) {
        messageRepo.save(message);
    }

    @Override
    public List<Message> getMessages(Chat chat, int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("date").ascending());
        Page<Message> page = messageRepo.findAllByChat(chat, pageRequest);
        return page.getContent();
    }
}
