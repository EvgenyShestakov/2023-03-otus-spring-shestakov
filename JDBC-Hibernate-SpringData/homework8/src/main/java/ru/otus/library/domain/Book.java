package ru.otus.library.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id;

    private String title;

    private LocalDate publicationDate;

    @DBRef
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Author author;

    @DBRef
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Genre genre;

    public Book(String id) {
        this.id = id;
    }

    public Book(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book(String title, LocalDate publicationDate, Author author, Genre genre) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.author = author;
        this.genre = genre;
    }
}
