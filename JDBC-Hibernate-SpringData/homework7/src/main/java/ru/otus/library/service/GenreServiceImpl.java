package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Genre;
import ru.otus.library.repositories.GenreRepositoryJpa;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepositoryJpa genreRepositoryJpa;

    @Transactional
    @Override
    public Genre saveGenre(String genreName) {
        Genre genre = new Genre(genreName);
        return genreRepositoryJpa.save(genre);
    }

    @Transactional
    @Override
    public void updateGenre(long id, String genreName) {
        Genre genre = new Genre(id, genreName);
        genreRepositoryJpa.save(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getGenreById(long id) {
        return genreRepositoryJpa.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAllGenres() {
        return genreRepositoryJpa.findAll();
    }

    @Transactional
    @Override
    public void deleteGenreById(long id) {
        Genre genre = genreRepositoryJpa.findById(id).orElseThrow(NoSuchElementException::new);
        genreRepositoryJpa.delete(genre);
    }
}
