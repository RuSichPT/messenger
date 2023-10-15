package com.github.rusichpt.Messenger.services;

import com.github.rusichpt.Messenger.models.Chat;
import com.github.rusichpt.Messenger.models.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(Message message);

    List<Message> getMessages(Chat chat, int pageNumber, int pageSize);
}
