package com.github.rusichpt.Messenger.dto;

import com.github.rusichpt.Messenger.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SignupRequest {
    @NotNull
    @NotBlank
    private final String username;
    @NotNull
    @NotBlank
    private final String password;
    @NotNull
    @Email
    private final String email;
    private final String name;
    private final String surname;

    public User toUser() {
        return new User(null, email, password, username, name, surname, false, UUID.randomUUID().toString());
    }
}
