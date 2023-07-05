package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.Application;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repositories.AuthorRepositoryJpa;
import ru.otus.library.repositories.BookRepositoryJpa;
import ru.otus.library.repositories.GenreRepositoryJpa;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = Application.class)
class BookServiceImplTest {
    @MockBean
    private BookRepositoryJpa bookRepositoryJpa;

    @MockBean
    private AuthorRepositoryJpa authorRepositoryJpa;

    @MockBean
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    void mustBeGetAll() {
        List<Book> books = Arrays.asList(new Book(1, "Flight", LocalDate.
                        of(1975, 1, 5),
                        new Author("John", "Smith"), new Genre("Mystic")),
                new Book(2, "It", LocalDate.of(1990, 5, 8),
                        new Author("Emily", "Johnson"), new Genre("Fantastic")));
        given(bookRepositoryJpa.findAll()).willReturn(books);
        assertEquals(2, bookService.getAllBooks().size());
    }

    @Test
    void mustGetById() {
        Book book = new Book(1, "Flight", LocalDate.of(1975, 1, 5),
                new Author("John", "Smith"), new Genre("Mystic"));
        given(bookRepositoryJpa.findById(1L)).willReturn(Optional.of(book));
        String title = bookService.getBookById(1).orElse(new Book(5, "It")).getTitle();
        assertEquals("Flight", title);
    }

    @Test
    void mustGetByIdNotFound() {
        given(bookRepositoryJpa.findById(1L)).willReturn(Optional.empty());
        Optional<Book> optionalBook = bookService.getBookById(1);
        assertFalse(optionalBook.isPresent());
    }

    @Test
    void mustDeleteById() {
        Book book = new Book(1, "Flight", LocalDate.of(1975, 1, 5),
                new Author("John", "Smith"), new Genre("Mystic"));
        given(bookRepositoryJpa.findById(1L)).willReturn(Optional.of(book));
        bookService.deleteBookById(1);
        verify(bookRepositoryJpa, times(1)).delete(book);
    }

    @Test
    void mustBeCreated() {
        String title = "Flight";
        LocalDate publicationDate = LocalDate.of(1999, 5, 3);
        Author author = new Author(1, "John", "Smith");
        Genre genre = new Genre(2, "Mystic");
        Book bookExpected = new Book(title, publicationDate, author, genre);
        given(authorRepositoryJpa.findById(1L)).willReturn(Optional.of(author));
        given(genreRepositoryJpa.findById(2L)).willReturn(Optional.of(genre));
        given(bookRepositoryJpa.save(bookExpected)).willReturn(bookExpected);
        Book bookActual = bookService.saveBook(title, publicationDate, 1, 2);
        assertEquals(bookExpected, bookActual);
    }

    @Test
    void whenSavingBookAuthorShouldNotBeFound() {
        given(authorRepositoryJpa.findById(1L)).willReturn(Optional.empty());
        given(genreRepositoryJpa.findById(1L)).willReturn(Optional.of(new Genre(1, "Mystic")));
        assertThrows(NoSuchElementException.class, () ->
                bookService.saveBook("Flight",
                        LocalDate.of(1999, 5, 3), 1, 2));
    }

    @Test
    void whenSavingBookGenreShouldNotBeFound() {
        given(authorRepositoryJpa.findById(1L)).willReturn(Optional.
                of(new Author(1, "John", "Smith",
                        LocalDate.of(1990, 1, 1))));
        given(genreRepositoryJpa.findById(1L)).willReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () ->
                bookService.saveBook("Flight",
                        LocalDate.of(1999, 5, 3), 1, 2));
    }

    @Test
    void mustBeUpdated() {
        long id = 5;
        String title = "Flight";
        LocalDate publicationDate = LocalDate.of(1999, 5, 3);
        Author author = new Author(1, "John", "Smith");
        Genre genre = new Genre(2, "Mystic");
        Book book = new Book(id, title, publicationDate, author, genre);
        given(authorRepositoryJpa.findById(1L)).willReturn(Optional.of(author));
        given(genreRepositoryJpa.findById(2L)).willReturn(Optional.of(genre));
        bookService.updateBook(id, title, publicationDate, 1, 2);
        verify(bookRepositoryJpa, times(1)).save(book);
    }

    @Test
    void whenUpdatedAuthorNotFound() {
        given(authorRepositoryJpa.findById(1L)).willReturn(Optional.empty());
        given(genreRepositoryJpa.findById(2L)).willReturn(Optional.of(new Genre(2, "Comedy")));
        assertThrows(NoSuchElementException.class, () ->
                bookService.updateBook(4, "Flight",
                        LocalDate.of(1999, 5, 3), 1, 2));
    }

    @Test
    void whenUpdatedGenreNotFound() {
        given(authorRepositoryJpa.findById(1L)).willReturn(Optional.
                of(new Author(1, "John", "Smith",
                        LocalDate.of(1990, 1, 1))));
        given(genreRepositoryJpa.findById(2L)).willReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () ->
                bookService.updateBook(4, "Flight",
                        LocalDate.of(1999, 5, 3), 1, 2));
    }
}
