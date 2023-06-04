package ru.otus.library.repositories;

import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Genre saveGenre(Genre genre);

    void updateGenre(Genre genre);

    Optional<Genre> getGenreById(long id);

    List<Genre> getAllGenres();

    void deleteGenreById(Genre genre);
}


