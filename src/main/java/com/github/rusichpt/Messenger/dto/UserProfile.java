package com.github.rusichpt.Messenger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfile {
    private String username;
    private String email;
    private String name;
    private String surname;
}
