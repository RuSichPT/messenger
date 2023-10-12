package com.github.rusichpt.Messenger.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private final String usernameTo;
    private final String content;
}
