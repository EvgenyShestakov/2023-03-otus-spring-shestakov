package ru.otus.library.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Import(CommentRepositoryJpa.class)
@DataJpaTest
class CommentRepositoryJpaTest {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void mustBeGetAll() {
        assertThat(commentRepository.getCommentsByBook(List.of(new Book(1)))).hasSize(1);
    }

    @Test
    void mustGetById() {
        Optional<Comment> genreOptional = commentRepository.getCommentById(1);
        assertTrue(genreOptional.isPresent());
        Comment comment = genreOptional.get();
        assertEquals("Wonderful book", comment.getBody());
    }

    @Test
    void mustBeUpdated() {
        Optional<Comment> beforeOptional = commentRepository.getCommentById(3);
        assertTrue(beforeOptional.isPresent());
        Comment beforeComment = beforeOptional.get();
        em.detach(beforeComment);
        commentRepository.updateComment(new Comment(3, "SuperComment", new Book(1)));
        Optional<Comment> afterOptional = commentRepository.getCommentById(3);
        assertTrue(afterOptional.isPresent());
        Comment afterComment = afterOptional.get();
        assertNotEquals(beforeComment.getBody(), afterComment.getBody());
    }

    @Test
    void mustBeCreated() {
        Comment commentToSave = new Comment("SuperComment", new Book(2));
        commentRepository.saveComment(commentToSave);
        Optional<Comment> savedCommentOptional = commentRepository.getCommentById(commentToSave.getId());
        assertTrue(savedCommentOptional.isPresent());
        Comment savedComment = savedCommentOptional.get();
        assertEquals(commentToSave.getBody(), commentToSave.getBody());
    }

    @Test
    void mustBeDeleted() {
        Optional<Comment> beforeOptional = commentRepository.getCommentById(3);
        assertTrue(beforeOptional.isPresent());
        Comment comment = beforeOptional.get();
        commentRepository.deleteCommentById(comment);
        Optional<Comment> afterOptional = commentRepository.getCommentById(3);
        assertFalse(afterOptional.isPresent());
    }
}
