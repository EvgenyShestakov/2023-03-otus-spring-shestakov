package ru.otus.library.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryJpaTest {
    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Test
    void mustBeGetAllBooks() {
        String expectedBookInfoByAuthorAndGenre = """
                Wei Wang - Horror
                Maria Garcia - Horror
                John Smith - Fantastic
                Emily Johnson - Comedy
                Michael Lee - Drama
                Michael Lee - Comedy""";
        String actualBookInfoByAuthorAndGenre = bookRepositoryJpa.findAll().stream().
                map(book -> book.getAuthor().getFirstName() + " " +
                        book.getAuthor().getLastName() + " - " + book.getGenre().
                        getGenreName()).collect(Collectors.joining("\n"));
        assertEquals(expectedBookInfoByAuthorAndGenre, actualBookInfoByAuthorAndGenre);
    }

    @Test
    void mustGetBookById() {
        String expectedBookInfoByAuthorAndGenre = "John Smith - Fantastic";
        Optional<Book> bookOptional = bookRepositoryJpa.findById(3L);
        assertTrue(bookOptional.isPresent());
        Book book = bookOptional.get();
        String actualBookInfoByAuthorAndGenre = book.getAuthor().getFirstName() + " " +
                book.getAuthor().getLastName() + " - " + book.getGenre().getGenreName();
        assertEquals(expectedBookInfoByAuthorAndGenre, actualBookInfoByAuthorAndGenre);
    }


    @Test
    void mustGetBookByTitle() {
        String expectedBookInfoByAuthorAndGenre = "The Lord of the Rings - John Smith - Fantastic";
        List<Book> books = bookRepositoryJpa.findBooksByTitle("The Lord of the Rings");
        assertFalse(books.isEmpty());
        Book book = books.get(0);
        String actualBookInfoByAuthorAndGenre = book.getTitle() + " - " +
                book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName() +
                " - " + book.getGenre().getGenreName();
        assertEquals(expectedBookInfoByAuthorAndGenre, actualBookInfoByAuthorAndGenre);
    }
}
