package ru.otus.library.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CommentRepositoryJpaTest {
    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Test
    void mustFindCommentsOnBook() {
        List<Comment> comments = commentRepositoryJpa.findCommentsByBookIn(List.of(new Book(4,
                "Flight", LocalDate.of(2001, 2, 6),
                new Author(4), new Genre(3))));
        String expectedComment = "Not interested";
        Comment comment = comments.get(0);
        String actualComment = comment.getBody();
        assertEquals(expectedComment, actualComment);
    }
}
