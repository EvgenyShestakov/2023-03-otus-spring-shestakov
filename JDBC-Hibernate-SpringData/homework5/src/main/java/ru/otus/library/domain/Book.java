package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Book {
    private long id;

    private String title;

    private LocalDate publicationDate;

    private Author author;

    private Genre genre;

    public Book(String title, LocalDate publicationDate, Author author, Genre genre) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.author = author;
        this.genre = genre;
    }
}
