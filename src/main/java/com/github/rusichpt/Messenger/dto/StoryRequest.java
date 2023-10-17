package com.github.rusichpt.Messenger.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StoryRequest {
    @NotNull
    @NotBlank
    private final String usernameFrom;
    private final int pageNumber;
    @Min(1)
    private final int pageSize;
}
