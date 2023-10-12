package com.github.rusichpt.Messenger.services.impl;

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
    public List<Message> getMessages(Long fromId, Long toId, int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());
        Page<Message> page = messageRepo.findAllByFromIdAndToId(fromId, toId, pageRequest);
        return page.getContent();
    }
}
