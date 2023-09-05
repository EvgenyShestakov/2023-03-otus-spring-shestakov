package ru.otus.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookRequestDto {
    private long bookId;

    @NotBlank(message = "Title must not be empty")
    private String title;

    private long authorId;

    private long genreId;
}
