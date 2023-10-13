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
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @Email
    @NotNull
    private String email;
    private String name;
    private String surname;

    public User toUser() {
        return new User(null, email, password, username, name, surname, false, UUID.randomUUID().toString());
    }
}
