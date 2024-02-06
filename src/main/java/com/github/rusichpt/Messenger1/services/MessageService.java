package com.github.rusichpt.Messenger1.services;

import com.github.rusichpt.Messenger1.entities.Message;
import com.github.rusichpt.Messenger1.entities.User;

import java.util.List;

public interface MessageService {
    void sendMessage(User from, User to, String content);

    List<Message> getMessages(User user1, User user2, int pageNumber, int pageSize);
}
