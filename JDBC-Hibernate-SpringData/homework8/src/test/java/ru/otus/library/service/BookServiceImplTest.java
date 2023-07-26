package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.Application;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.AuthorNotFoundException;
import ru.otus.library.exception.GenreNotFoundException;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Application.class)
class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    void mustBeGetAll() {
        List<Book> books = Arrays.asList(new Book("1", "Flight", LocalDate.
                        of(1975, 1, 5),
                        new Author("John", "Smith"), new Genre("Mystic")),
                new Book("2", "It", LocalDate.of(1990, 5, 8),
                        new Author("Emily", "Johnson"), new Genre("Fantastic")));
        doReturn(books).when(bookRepository).findAll();
        assertEquals(2, bookService.getAllBooks().size());
    }

    @Test
    void mustGetById() {
        Book book = new Book("1", "Flight", LocalDate.of(1975, 1, 5),
                new Author("John", "Smith"), new Genre("Mystic"));
        doReturn(Optional.of(book)).when(bookRepository).findById("1");
        String title = bookService.getBookById("1").orElse(new Book("5", "It")).getTitle();
        assertEquals("Flight", title);
    }

    @Test
    void mustGetByIdNotFound() {
        doReturn(Optional.empty()).when(bookRepository).findById("1");
        Optional<Book> optionalBook = bookService.getBookById("1");
        assertFalse(optionalBook.isPresent());
    }

    @Test
    void mustDeleteById() {
        Book book = new Book("1", "Flight", LocalDate.of(1975, 1, 5),
                new Author("John", "Smith"), new Genre("Mystic"));
        doReturn(Optional.of(book)).when(bookRepository).findById("1");
        bookService.deleteBookById("1");
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void mustBeCreated() {
        String title = "Flight";
        LocalDate publicationDate = LocalDate.of(1999, 5, 3);
        Author author = new Author("1", "John", "Smith");
        Genre genre = new Genre("2", "Mystic");
        Book bookExpected = new Book(title, publicationDate, author, genre);
        doReturn(Optional.of(author)).when(authorRepository).findById("1");
        doReturn(Optional.of(genre)).when(genreRepository).findById("2");
        doReturn(bookExpected).when(bookRepository).save(bookExpected);
        Book bookActual = bookService.saveBook(title, publicationDate, "1", "2");
        assertEquals(bookExpected, bookActual);
    }

    @Test
    void whenSavingBookAuthorShouldNotBeFound() {
        doReturn(Optional.empty()).when(authorRepository).findById("1");
        doReturn(Optional.of(new Genre("1", "Mystic"))).when(genreRepository).findById("1");
        assertThrows(AuthorNotFoundException.class, () ->
                bookService.saveBook("Flight",
                        LocalDate.of(1999, 5, 3), "1", "2"));
    }

    @Test
    void whenSavingBookGenreShouldNotBeFound() {
        doReturn(Optional.
                of(new Author("1", "John", "Smith",
                        LocalDate.of(1990, 1, 1)))).when(authorRepository).findById("1");
        doReturn(Optional.empty()).when(genreRepository).findById("1");
        assertThrows(GenreNotFoundException.class, () ->
                bookService.saveBook("Flight",
                        LocalDate.of(1999, 5, 3), "1", "2"));
    }

    @Test
    void mustBeUpdated() {
        String id = "5";
        String title = "Flight";
        LocalDate publicationDate = LocalDate.of(1999, 5, 3);
        Author author = new Author("1", "John", "Smith");
        Genre genre = new Genre("2", "Mystic");
        Book book = new Book(id, title, publicationDate, author, genre);
        doReturn(Optional.of(author)).when(authorRepository).findById("1");
        doReturn(Optional.of(genre)).when(genreRepository).findById("2");
        bookService.updateBook(id, title, publicationDate, "1", "2");
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void whenUpdatedAuthorNotFound() {
        doReturn(Optional.empty()).when(authorRepository).findById("1");
        doReturn(Optional.of(new Genre("2", "Comedy"))).when(genreRepository).findById("2");
        assertThrows(AuthorNotFoundException.class, () ->
                bookService.updateBook("4", "Flight",
                        LocalDate.of(1999, 5, 3), "1", "2"));
    }

    @Test
    void whenUpdatedGenreNotFound() {
        doReturn(Optional.of(new Author("1", "John", "Smith",
                LocalDate.of(1990, 1, 1)))).when(authorRepository).findById("1");
        doReturn(Optional.empty()).when(genreRepository).findById("2");
        assertThrows(GenreNotFoundException.class, () ->
                bookService.updateBook("4", "Flight",
                        LocalDate.of(1999, 5, 3), "1", "2"));
    }
}
