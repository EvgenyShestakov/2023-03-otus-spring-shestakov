package ru.otus.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = "book")
    List<Comment> findCommentsByBookOrderById(Book book);
}
