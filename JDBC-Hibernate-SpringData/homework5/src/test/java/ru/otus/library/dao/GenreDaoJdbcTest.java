package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.otus.library.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.yml")
@Import({GenreDaoJdbc.class, BookDaoJdbc.class})
@JdbcTest
class GenreDaoJdbcTest {
    @Autowired
    private GenreDao genreDao;

    @Autowired
    private BookDao bookDao;

    @Test
    void mustBeGetAll() {
        assertThat(genreDao.getAll()).hasSize(5);
    }

    @Test
    void mustGetById() {
        Optional<Genre> genreOptional = genreDao.getById(4);
        assertTrue(genreOptional.isPresent());
        Genre genre = genreOptional.get();
        assertEquals("Comedy", genre.getGenreName());
    }

    @Test
    void mustBeUpdated() {
        Optional<Genre> beforeOptional = genreDao.getById(3);
        assertTrue(beforeOptional.isPresent());
        Genre comedy = beforeOptional.get();
        genreDao.update(new Genre(3, "Adventure"));
        Optional<Genre> afterOptional = genreDao.getById(3);
        assertTrue(afterOptional.isPresent());
        Genre action = afterOptional.get();
        assertNotEquals(comedy.getGenreName(), action.getGenreName());
    }

    @Test
    void mustBeCreated() {
        Genre genreToSave = new Genre("Action");
        genreDao.save(genreToSave);
        Optional<Genre> savedGenreOptional = genreDao.getById(genreToSave.getId());
        assertTrue(savedGenreOptional.isPresent());
        Genre savedGenre = savedGenreOptional.get();
        assertEquals(savedGenre.getGenreName(), genreToSave.getGenreName());
    }

    @Test
    void mustBeDeleted() {
        Optional<Genre> beforeOptional = genreDao.getById(3);
        assertTrue(beforeOptional.isPresent());
        bookDao.deleteById(4);
        genreDao.deleteById(4);
        Optional<Genre> afterOptional = genreDao.getById(4);
        assertFalse(afterOptional.isPresent());
    }
}
