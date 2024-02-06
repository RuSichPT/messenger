package com.github.rusichpt.Messenger1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryRequest {
    @NotNull
    @NotBlank
    private String usernameFrom;
    private int pageNumber;
    @Min(1)
    private int pageSize;
}
