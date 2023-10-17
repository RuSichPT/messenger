package com.github.rusichpt.Messenger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponse {
    private String username1;
    private String username2;
    private String content;
    private LocalDateTime date;
}
