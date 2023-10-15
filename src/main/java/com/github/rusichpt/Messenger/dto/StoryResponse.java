package com.github.rusichpt.Messenger.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryResponse {
    private final String username1;
    private final String username2;
    private final String content;
    private final LocalDateTime date;
}
