package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repositories.AuthorRepositoryJpa;
import ru.otus.library.repositories.BookRepositoryJpa;
import ru.otus.library.repositories.GenreRepositoryJpa;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepositoryJpa bookRepositoryJpa;

    private final AuthorRepositoryJpa authorRepositoryJpa;

    private final GenreRepositoryJpa genreRepositoryJpa;

    @Transactional
    @Override
    public Book saveBook(String title, LocalDate publicationDate, long authorId, long genreId)
            throws NoSuchElementException {
        Author author = authorRepositoryJpa.findById(authorId).orElseThrow(NoSuchElementException::new);
        Genre genre = genreRepositoryJpa.findById(genreId).orElseThrow(NoSuchElementException::new);
        Book book = new Book(title, publicationDate, author, genre);
        return bookRepositoryJpa.save(book);
    }

    @Transactional
    @Override
    public void updateBook(long id, String title, LocalDate publicationDate, long authorId, long genreId)
            throws NoSuchElementException {
        Author author = authorRepositoryJpa.findById(authorId).orElseThrow(NoSuchElementException::new);
        Genre genre = genreRepositoryJpa.findById(genreId).orElseThrow(NoSuchElementException::new);
        Book book = new Book(id, title, publicationDate, author, genre);
        bookRepositoryJpa.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBookByTitle(String title) {
        return bookRepositoryJpa.findBooksByTitle(title);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepositoryJpa.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepositoryJpa.findAll();
    }

    @Transactional
    @Override
    public void deleteBookById(long id) {
        Book book = bookRepositoryJpa.findById(id).orElseThrow(NoSuchElementException::new);
        bookRepositoryJpa.delete(book);
    }
}
