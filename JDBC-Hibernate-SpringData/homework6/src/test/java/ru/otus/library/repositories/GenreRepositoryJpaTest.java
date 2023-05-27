package ru.otus.library.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Import(GenreRepositoryJpa.class)
@DataJpaTest
class GenreRepositoryJpaTest {
    @Autowired
    private GenreRepository genreRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void mustBeGetAll() {
        assertThat(genreRepository.getAllGenres()).hasSize(6);
    }

    @Test
    void mustGetById() {
        Optional<Genre> genreOptional = genreRepository.getGenreById(4);
        assertTrue(genreOptional.isPresent());
        Genre genre = genreOptional.get();
        assertEquals("Comedy", genre.getGenreName());
    }

    @Test
    void mustBeUpdated() {
        Optional<Genre> beforeOptional = genreRepository.getGenreById(3);
        assertTrue(beforeOptional.isPresent());
        Genre comedy = beforeOptional.get();
        em.detach(comedy);
        genreRepository.updateGenre(new Genre(3, "Adventure"));
        Optional<Genre> afterOptional = genreRepository.getGenreById(3);
        assertTrue(afterOptional.isPresent());
        Genre action = afterOptional.get();
        assertNotEquals(comedy.getGenreName(), action.getGenreName());
    }

    @Test
    void mustBeCreated() {
        Genre genreToSave = new Genre("Action");
        genreRepository.saveGenre(genreToSave);
        Optional<Genre> savedGenreOptional = genreRepository.getGenreById(genreToSave.getId());
        assertTrue(savedGenreOptional.isPresent());
        Genre savedGenre = savedGenreOptional.get();
        assertEquals(savedGenre.getGenreName(), genreToSave.getGenreName());
    }

    @Test
    void mustBeDeleted() {
        Optional<Genre> beforeOptional = genreRepository.getGenreById(6);
        assertTrue(beforeOptional.isPresent());
        Genre genre = beforeOptional.get();
        em.detach(genre);
        genreRepository.deleteGenreById(6);
        Optional<Genre> afterOptional = genreRepository.getGenreById(6);
        assertFalse(afterOptional.isPresent());
    }
}
