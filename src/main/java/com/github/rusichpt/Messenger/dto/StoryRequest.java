package com.github.rusichpt.Messenger.dto;

import lombok.Data;

@Data
public class StoryRequest {
    private final String usernameFrom;
    private final int pageNumber;
    private final int pageSize;
}
