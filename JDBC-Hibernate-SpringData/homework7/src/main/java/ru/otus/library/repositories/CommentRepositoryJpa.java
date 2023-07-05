package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentRepositoryJpa extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByBookIn(List<Book> books);
}
