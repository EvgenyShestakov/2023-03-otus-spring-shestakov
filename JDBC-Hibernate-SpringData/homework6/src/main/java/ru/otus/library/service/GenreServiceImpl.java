package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Genre;
import ru.otus.library.repositories.GenreRepository;
import ru.otus.library.util.Converter;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final Converter<Genre> converter;

    @Transactional
    @Override
    public Genre saveGenre(String genreName) {
        Genre genre = new Genre(genreName);
        return genreRepository.saveGenre(genre);
    }

    @Transactional
    @Override
    public boolean updateGenre(long id, String genreName) {
        Genre genre = new Genre(id, genreName);
        return genreRepository.updateGenre(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getGenreById(long id) {
        return genreRepository.getGenreById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.getAllGenres();
    }

    @Transactional
    @Override
    public boolean deleteGenreById(long id) {
        return genreRepository.deleteGenreById(id);
    }
}
