package com.github.rusichpt.Messenger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PassRequest {
    @NotNull
    @NotBlank
    private final String password;
}
