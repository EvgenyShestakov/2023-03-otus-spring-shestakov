package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Import(BookDaoJdbc.class)
@JdbcTest
class BookDaoJdbcTest {
    @Autowired
    private BookDao bookDao;

    @Test
    void mustBeGetAll() {
        assertThat(bookDao.getAll()).hasSize(5);
    }

    @Test
    void mustGetById() {
        Optional<Book> bookOptional = bookDao.getById(2);
        assertTrue(bookOptional.isPresent());
        Book book = bookOptional.get();
        assertEquals("It", book.getTitle());
    }

    @Test
    void mustBeUpdated() {
        Optional<Book> beforeOptional = bookDao.getById(2);
        assertTrue(beforeOptional.isPresent());
        Book it = beforeOptional.get();
        bookDao.update(new Book(2, "Flight",
                LocalDate.of(2001, 2, 6), new Author(4), new Genre(3)));
        Optional<Book> afterOptional = bookDao.getById(2);
        assertTrue(afterOptional.isPresent());
        Book flight = afterOptional.get();
        assertNotEquals(it.getTitle(), flight.getTitle());
    }

    @Test
    void mustBeCreated() {
        Book bookToSave = new Book("Flight", LocalDate.
                of(2001, 2, 6), new Author(4), new Genre(3));
        bookDao.save(bookToSave);
        Optional<Book> savedBookOptional = bookDao.getById(bookToSave.getId());
        assertTrue(savedBookOptional.isPresent());
        Book savedBook = savedBookOptional.get();
        assertEquals(savedBook.getTitle(), bookToSave.getTitle());
    }

    @Test
    void mustBeDeleted() {
        Optional<Book> beforeOptional = bookDao.getById(2);
        assertTrue(beforeOptional.isPresent());
        bookDao.deleteById(2);
        Optional<Book> afterOptional = bookDao.getById(2);
        assertFalse(afterOptional.isPresent());
    }
}
