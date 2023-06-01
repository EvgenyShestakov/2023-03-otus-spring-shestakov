package ru.otus.library.service;

import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Genre saveGenre(String genreName);

    void updateGenre(long id, String genreName);

    Optional<Genre> getGenreById(long id);

    List<Genre> getAllGenres();

    void deleteGenreById(long id);
}
