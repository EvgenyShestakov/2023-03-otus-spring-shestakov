package ru.otus.library.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Genre saveGenre(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public void updateGenre(Genre genre) {
        em.merge(genre);
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> getAllGenres() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public void deleteGenreById(Genre genre) {
        em.remove(genre);
    }
}
