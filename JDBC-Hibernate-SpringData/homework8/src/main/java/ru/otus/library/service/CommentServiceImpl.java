package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.exception.BookNotFoundException;
import ru.otus.library.exception.CommentNotFoundException;
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
    public Comment saveComment(String body, String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Comment comment = new Comment(body, book);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void updateComment(String id, String body, String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Comment comment = new Comment(id, body, book);
        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getCommentById(String id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getCommentsByBook(String title) throws NoSuchElementException {
        List<Book> books = bookRepository.findBooksByTitle(title);
        return commentRepository.findCommentsByBookIn(books);
    }

    @Transactional
    @Override
    public void deleteCommentById(String id) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(comment);
    }
}
