package com.github.rusichpt.Messenger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {
    @NotNull
    private final String username;
    @NotBlank
    @NotNull
    private final String password;
}
