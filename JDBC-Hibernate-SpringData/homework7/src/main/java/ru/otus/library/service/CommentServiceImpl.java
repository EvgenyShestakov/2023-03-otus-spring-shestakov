package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repositories.BookRepositoryJpa;
import ru.otus.library.repositories.CommentRepositoryJpa;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepositoryJpa commentRepositoryJpa;

    private final BookRepositoryJpa bookRepositoryJpa;

    @Transactional
    @Override
    public Comment saveComment(String body, long bookId) throws NoSuchElementException {
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(NoSuchElementException::new);
        Comment comment = new Comment(body, book);
        return commentRepositoryJpa.save(comment);
    }

    @Transactional
    @Override
    public void updateComment(long id, String body, long bookId) throws NoSuchElementException {
        Book book = bookRepositoryJpa.findById(bookId).orElseThrow(NoSuchElementException::new);
        Comment comment = new Comment(id, body, book);
        commentRepositoryJpa.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getCommentById(long id) {
        return commentRepositoryJpa.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getCommentsByBook(String title) throws NoSuchElementException {
        List<Book> books = bookRepositoryJpa.findBooksByTitle(title);
        return commentRepositoryJpa.findCommentsByBookIn(books);
    }

    @Transactional
    @Override
    public void deleteCommentById(long id) {
        Comment comment = commentRepositoryJpa.findById(id).orElseThrow(NoSuchElementException::new);
        commentRepositoryJpa.delete(comment);
    }
}
