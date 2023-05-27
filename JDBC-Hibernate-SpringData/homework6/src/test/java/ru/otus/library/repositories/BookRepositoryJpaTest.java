package ru.otus.library.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Import(BookRepositoryJpa.class)
@DataJpaTest
class BookRepositoryJpaTest {
    @Autowired
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void mustBeGetAll() {
        assertThat(bookRepository.getAllBooks()).hasSize(6);
    }

    @Test
    void mustGetById() {
        Optional<Book> bookOptional = bookRepository.getBookById(2);
        assertTrue(bookOptional.isPresent());
        Book book = bookOptional.get();
        assertEquals("It", book.getTitle());
    }

    @Test
    void mustBeUpdated() {
        Optional<Book> beforeOptional = bookRepository.getBookById(2);
        assertTrue(beforeOptional.isPresent());
        Book it = beforeOptional.get();
        em.detach(it);
        bookRepository.updateBook(new Book(2, "Flight",
                LocalDate.of(2001, 2, 6), new Author(4), new Genre(3)));
        Optional<Book> afterOptional = bookRepository.getBookById(2);
        assertTrue(afterOptional.isPresent());
        Book flight = afterOptional.get();
        assertNotEquals(it.getTitle(), flight.getTitle());
    }

    @Test
    void mustBeCreated() {
        Book bookToSave = new Book("Flight", LocalDate.
                of(2001, 2, 6), new Author(4), new Genre(3));
        bookRepository.saveBook(bookToSave);
        Optional<Book> savedBookOptional = bookRepository.getBookById(bookToSave.getId());
        assertTrue(savedBookOptional.isPresent());
        Book savedBook = savedBookOptional.get();
        assertEquals(savedBook.getTitle(), bookToSave.getTitle());
    }

    @Test
    void mustBeDeleted() {
        Optional<Book> beforeOptional = bookRepository.getBookById(6);
        assertTrue(beforeOptional.isPresent());
        Book it = beforeOptional.get();
        em.detach(it);
        bookRepository.deleteBookById(6);
        Optional<Book> afterOptional = bookRepository.getBookById(6);
        assertFalse(afterOptional.isPresent());
    }
}
