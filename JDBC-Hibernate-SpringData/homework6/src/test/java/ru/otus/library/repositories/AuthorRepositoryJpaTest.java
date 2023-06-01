package ru.otus.library.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Import(AuthorRepositoryJpa.class)
@DataJpaTest
class AuthorRepositoryJpaTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void mustBeGetAll() {
        assertThat(authorRepository.getAllAuthors()).hasSize(6);
    }

    @Test
    void mustGetById() {
        Optional<Author> authorOptional = authorRepository.getAuthorById(3);
        assertTrue(authorOptional.isPresent());
        Author author = authorOptional.get();
        assertEquals("Michael", author.getFirstName());
    }

    @Test
    void mustBeUpdated() {
        Optional<Author> beforeOptional = authorRepository.getAuthorById(3);
        assertTrue(beforeOptional.isPresent());
        Author lee = beforeOptional.get();
        em.detach(lee);
        authorRepository.updateAuthor(new Author(3, "Tony",
                "Montano", LocalDate.of(1964, 4, 23)));
        Optional<Author> afterOptional = authorRepository.getAuthorById(3);
        assertTrue(afterOptional.isPresent());
        Author tony = afterOptional.get();
        assertNotEquals(lee.getFirstName(), tony.getFirstName());
    }

    @Test
    void mustBeCreated() {
        Author authorToSave = new Author("John", "Doe", LocalDate.of(1975, 6, 13));
        authorRepository.saveAuthor(authorToSave);
        Optional<Author> savedAuthorOptional = authorRepository.getAuthorById(authorToSave.getId());
        assertTrue(savedAuthorOptional.isPresent());
        Author savedAuthor = savedAuthorOptional.get();
        assertEquals(savedAuthor.getFirstName(), authorToSave.getFirstName());
    }

    @Test
    void mustBeDeleted() {
        Optional<Author> beforeOptional = authorRepository.getAuthorById(6);
        assertTrue(beforeOptional.isPresent());
        Author author = beforeOptional.get();
        authorRepository.deleteAuthorById(author);
        Optional<Author> afterOptional = authorRepository.getAuthorById(6);
        assertFalse(afterOptional.isPresent());
    }
}
