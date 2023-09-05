package ru.otus.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequestDto {
    private long bookId;

    @NotBlank(message = "Comment body must not be empty")
    private String body;
}
