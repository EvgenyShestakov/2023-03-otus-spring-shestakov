package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.Application;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.dao.BookDao;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.util.Converter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Application.class)
class BookServiceImplTest {
    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @MockBean
    private OutputService outputService;

    @Autowired
    private Converter<Book> converter;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    void mustBeGetAll() {
        List<Book> books = Arrays.asList(new Book(1, "Flight", LocalDate.
                of(1975, 1, 5),
                new Author("John", "Smith"), new Genre("Mystic")),
                new Book(2, "It", LocalDate.of(1990, 5, 8),
                        new Author("Emily", "Johnson"), new Genre("Fantastic")));
        given(bookDao.getAll()).willReturn(books);
        bookService.getAll();
        verify(outputService, times(2)).outputString(anyString());
        assertEquals(2, books.size());
    }

    @Test
    void mustGetById() {
        Book book = new Book(1, "Flight", LocalDate.of(1975, 1, 5),
                new Author("John", "Smith"), new Genre("Mystic"));
        given(bookDao.getById(book.getId())).willReturn(Optional.of(book));
        bookService.getById(book.getId());
        verify(outputService).outputString(converter.convert(book));
    }

    @Test
    void mustGetByIdNotFound() {
        long id = 1;
        given(bookDao.getById(id)).willReturn(Optional.empty());
        bookService.getById(id);
        verify(outputService).outputString("There is no book with this ID");
    }

    @Test
    void mustDeleteById() {
        long id = 1;
        given(bookDao.deleteById(id)).willReturn(true);
        bookService.deleteById(id);
        verify(outputService).outputString("true");
        verify(bookDao).deleteById(id);
    }

    @Test
    void mustBeCreated() {
        String title = "Flight";
        LocalDate publicationDate = LocalDate.of(1999, 5, 3);
        Author author = new Author(1, "John", "Smith");
        Genre genre = new Genre(2,"Mystic");
        Book book = new Book(title, publicationDate, author, genre);
        given(authorDao.getById(1)).willReturn(Optional.of(author));
        given(genreDao.getById(2)).willReturn(Optional.of(genre));
        given(bookDao.save(book)).willReturn(book);
        bookService.save(title, publicationDate, 1, 2);
        verify(outputService).outputString(converter.convert(book));
        verify(bookDao).save(book);
    }

    @Test
    void whenSavingBookAuthorShouldNotBeFound() {
        given(authorDao.getById(1)).willReturn(Optional.empty());
        bookService.save("Flight", LocalDate.of(1999, 5, 3), 1, 2);
        verify(outputService).outputString("Author with ID = 1 not found");
        verify(bookDao, never()).save(any());
    }

    @Test
    void whenSavingBookGenreShouldNotBeFound() {
        given(genreDao.getById(2)).willReturn(Optional.empty());
        bookService.save("Flight", LocalDate.of(1999, 5, 3), 1, 2);
        verify(outputService).outputString("Genre with ID = 2 not found");
        verify(bookDao, never()).save(any());
    }

    @Test
    void mustBeUpdated() {
        long id = 5;
        String title = "Flight";
        LocalDate publicationDate = LocalDate.of(1999, 5, 3);
        Author author = new Author(1,"John", "Smith");
        Genre genre = new Genre(2,"Mystic");
        Book book = new Book(id, title, publicationDate, author, genre);
        given(authorDao.getById(1)).willReturn(Optional.of(author));
        given(genreDao.getById(2)).willReturn(Optional.of(genre));
        given(bookDao.update(book)).willReturn(true);
        bookService.update(id, title, publicationDate, 1, 2);
        verify(outputService).outputString("true");
        verify(bookDao).update(book);
    }

    @Test
    void whenUpdatedAuthorNotFound() {
        given(authorDao.getById(1)).willReturn(Optional.empty());
        bookService.update(4, "Flight", LocalDate.of(1999, 5, 3), 1, 2);
        verify(outputService).outputString("Author with ID = 1 not found");
        verify(bookDao, never()).update(any());
    }

    @Test
    void whenUpdatedGenreNotFound() {
        given(genreDao.getById(2)).willReturn(Optional.empty());
        bookService.update(4, "Flight", LocalDate.of(1999, 5, 3), 1, 2);
        verify(outputService).outputString("Genre with ID = 2 not found");
        verify(bookDao, never()).update(any());
    }
}
