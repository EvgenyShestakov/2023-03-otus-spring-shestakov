package ru.otus.library.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    private String body;

    @DBRef
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Book book;

    public Comment(String body, Book book) {
        this.body = body;
        this.book = book;
    }
}
