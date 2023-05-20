package ru.otus.library.dao;

import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Genre save(Genre genre);

    boolean update(Genre genre);

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    boolean deleteById(long id);
}
