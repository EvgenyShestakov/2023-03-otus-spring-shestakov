package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
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

    @Transactional
    @Override
    public Book saveBook(String title, LocalDate publicationDate, long authorId, long genreId)
            throws NoSuchElementException {
        Author author = authorRepository.getAuthorById(authorId).orElseThrow();
        Genre genre = genreRepository.getGenreById(genreId).orElseThrow();
        Book book = new Book(title, publicationDate, author, genre);
        return bookRepository.saveBook(book);
    }

    @Transactional
    @Override
    public void updateBook(long id, String title, LocalDate publicationDate, long authorId, long genreId)
            throws NoSuchElementException {
        Author author = authorRepository.getAuthorById(authorId).orElseThrow();
        Genre genre = genreRepository.getGenreById(genreId).orElseThrow();
        Book book = new Book(id, title, publicationDate, author, genre);
        bookRepository.updateBook(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBookByTitle(String title) {
        return bookRepository.getBookByTitle(title);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.getBookById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Transactional
    @Override
    public void deleteBookById(long id) {
        Book book = bookRepository.getBookById(id).orElseThrow();
        bookRepository.deleteBookById(book);
    }
}
