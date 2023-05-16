package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Import({AuthorDaoJdbc.class, BookDaoJdbc.class})
@JdbcTest
class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private BookDao bookDao;

    @Test
    void mustBeGetAll() {
        assertThat(authorDao.getAll()).hasSize(5);
    }

    @Test
    void mustGetById() {
        Optional<Author> authorOptional = authorDao.getById(3);
        assertTrue(authorOptional.isPresent());
        Author author = authorOptional.get();
        assertEquals("Michael", author.getFirstName());
    }

    @Test
    void mustBeUpdated() {
        Optional<Author> beforeOptional = authorDao.getById(3);
        assertTrue(beforeOptional.isPresent());
        Author lee = beforeOptional.get();
        authorDao.update(new Author(3, "Tony",
                "Montano", LocalDate.of(1964, 4, 23)));
        Optional<Author> afterOptional = authorDao.getById(3);
        assertTrue(afterOptional.isPresent());
        Author tony = afterOptional.get();
        assertNotEquals(lee.getFirstName(), tony.getFirstName());
    }

    @Test
    void mustBeCreated() {
        Author authorToSave = new Author("John", "Doe", LocalDate.of(1975, 6, 13));
        authorDao.save(authorToSave);
        Optional<Author> savedAuthorOptional = authorDao.getById(authorToSave.getId());
        assertTrue(savedAuthorOptional.isPresent());
        Author savedAuthor = savedAuthorOptional.get();
        assertEquals(savedAuthor.getFirstName(), authorToSave.getFirstName());
    }

    @Test
    void mustBeDeleted() {
        Optional<Author> beforeOptional = authorDao.getById(3);
        assertTrue(beforeOptional.isPresent());
        bookDao.deleteById(5);
        authorDao.deleteById(3);
        Optional<Author> afterOptional = authorDao.getById(3);
        assertFalse(afterOptional.isPresent());
    }
}
