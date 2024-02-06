package com.github.rusichpt.Messenger1.dto;

import com.github.rusichpt.Messenger1.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @Email
    private String email;
    private String name;
    private String surname;

    public User toUser() {
        return new User(null, email, password, username, name, surname, false, UUID.randomUUID().toString());
    }
}
