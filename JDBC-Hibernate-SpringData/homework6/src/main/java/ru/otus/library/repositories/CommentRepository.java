package ru.otus.library.repositories;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment saveComment(Comment comment);

    void updateComment(Comment comment);

    Optional<Comment> getCommentById(long id);

    List<Comment> getCommentsByBook(List<Book> book);

    void deleteCommentById(Comment comment);
}
