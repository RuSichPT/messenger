package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.models.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(Message message);

    List<Message> getMessages(Long fromId, Long toId, int pageNumber, int pageSize);
}
