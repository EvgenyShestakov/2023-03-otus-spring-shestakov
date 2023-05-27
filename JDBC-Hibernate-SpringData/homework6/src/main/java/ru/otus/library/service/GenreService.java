package ru.otus.library.service;

import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Genre saveGenre(String genreName);

    boolean updateGenre(long id, String genreName);

    Optional<Genre> getGenreById(long id);

    List<Genre> getAllGenres();

    boolean deleteGenreById(long id);
}
