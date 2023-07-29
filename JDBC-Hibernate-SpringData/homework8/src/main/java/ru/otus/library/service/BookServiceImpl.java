package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.AuthorNotFoundException;
import ru.otus.library.exception.BookNotFoundException;
import ru.otus.library.exception.GenreNotFoundException;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.CommentRepository;
import ru.otus.library.repositories.GenreRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public Book saveBook(String title, LocalDate publicationDate, String authorId, String genreId)
            throws NoSuchElementException {
        Author author = authorRepository.findById(authorId).orElseThrow(AuthorNotFoundException::new);
        Genre genre = genreRepository.findById(genreId).orElseThrow(GenreNotFoundException::new);
        Book book = new Book(title, publicationDate, author, genre);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void updateBook(String id, String title, LocalDate publicationDate, String authorId, String genreId)
            throws NoSuchElementException {
        Author author = authorRepository.findById(authorId).orElseThrow(AuthorNotFoundException::new);
        Genre genre = genreRepository.findById(genreId).orElseThrow(GenreNotFoundException::new);
        Book book = new Book(id, title, publicationDate, author, genre);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBookByTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteBookById(String id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        commentRepository.deleteAllCommentsByBookId(id);
        bookRepository.delete(book);
    }
}
