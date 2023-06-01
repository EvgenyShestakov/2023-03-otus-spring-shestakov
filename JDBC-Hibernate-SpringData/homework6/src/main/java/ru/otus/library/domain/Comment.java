package ru.otus.library.domain;

import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
@NamedEntityGraph(name = "comment-book-entity-graph",
        attributeNodes = {@NamedAttributeNode("book")})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "body", nullable = false)
    private String body;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public Comment(String body, Book book) {
        this.body = body;
        this.book = book;
    }
}
