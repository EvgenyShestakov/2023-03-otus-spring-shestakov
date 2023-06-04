package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.CommentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public Comment saveComment(String body, long bookId) throws NoSuchElementException {
        Book book = bookRepository.getBookById(bookId).orElseThrow();
        Comment comment = new Comment(body, book);
        return commentRepository.saveComment(comment);
    }

    @Transactional
    @Override
    public void updateComment(long id, String body, long bookId) throws NoSuchElementException {
        Book book = bookRepository.getBookById(bookId).orElseThrow();
        Comment comment = new Comment(id, body, book);
        commentRepository.updateComment(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getCommentById(long id) {
        return commentRepository.getCommentById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getCommentsByBook(String title) throws NoSuchElementException {
        List<Book> books = bookRepository.getBookByTitle(title);
        return commentRepository.getCommentsByBook(books);
    }

    @Transactional
    @Override
    public void deleteCommentById(long id) {
        Comment comment = commentRepository.getCommentById(id).orElseThrow();
        commentRepository.deleteCommentById(comment);
    }
}
