package com.github.rusichpt.Messenger.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final Long fromId;
    private final Long toId;
    private final String content;
    private final LocalDate date = LocalDate.now();
//    private boolean isRead;
}
