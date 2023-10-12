package com.github.rusichpt.Messenger.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Message {
    private final String usernameFrom;
    private final String usernameTo;
    private final String content;
    private final LocalDate date;
}
