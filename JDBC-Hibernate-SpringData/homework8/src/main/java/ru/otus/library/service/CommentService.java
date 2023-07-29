package ru.otus.library.service;

import ru.otus.library.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment saveComment(String body, String bookId);

    void updateComment(String id, String body, String bookId);

    Optional<Comment> getCommentById(String id);

    List<Comment> getCommentsByBook(String bookTitle);

    void deleteCommentById(String id);
}
