package com.github.rusichpt.Messenger.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @Email
    private String email;
    private String name;
    private String surname;
}
