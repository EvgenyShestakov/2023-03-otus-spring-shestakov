package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentRequestDto;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public Comment saveComment(CommentRequestDto commentRequestDto) throws NoSuchElementException {
        long bookId = commentRequestDto.getBookId();
        String body = commentRequestDto.getBody();
        Book book = bookRepository.findById(bookId).orElseThrow(NoSuchElementException::new);
        Comment comment = new Comment(body, book);
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getCommentsByBook(Book book) throws NoSuchElementException {
        return commentRepository.findCommentsByBookOrderById(book);
    }
}
