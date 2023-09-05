package ru.otus.library.service;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentRequestDto;

import java.util.List;

public interface CommentService {
    Comment saveComment(CommentRequestDto commentRequestDto);

    List<Comment> getCommentsByBook(Book book);
}
