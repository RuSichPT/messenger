package com.github.rusichpt.messenger.service;

import com.github.rusichpt.messenger.entity.Message;
import com.github.rusichpt.messenger.entity.User;

import java.util.List;

public interface MessageService {
    void sendMessage(User from, User to, String content);

    List<Message> getMessages(User user1, User user2, int pageNumber, int pageSize);
}
