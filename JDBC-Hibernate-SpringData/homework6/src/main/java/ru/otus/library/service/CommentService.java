package ru.otus.library.service;

import ru.otus.library.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment saveComment(String body, long bookId);

    void updateComment(long id, String body, long bookId);

    Optional<Comment> getCommentById(long id);

    List<Comment> getCommentsByBook(String bookTitle);

    void deleteCommentById(long id);
}
