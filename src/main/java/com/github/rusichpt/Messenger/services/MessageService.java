package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.entities.Chat;
import com.github.rusichpt.Messenger.entities.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(Message message);

    List<Message> getMessages(Chat chat, int pageNumber, int pageSize);
}
