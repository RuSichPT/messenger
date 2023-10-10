package com.github.rusichpt.Messenger.dto;

import com.github.rusichpt.Messenger.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupRequest {
    @Email
    @NotNull
    private String email;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String username;
    private String name;
    private String surname;

    public User toUser() {
        return new User(null, email, password, username, name, surname);
    }
}
