package ru.otus.library.dao;

import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Author save(Author author);

    boolean update(Author author);

    Optional<Author> getById(long id);

    List<Author> getAll();

    boolean deleteById(long id);
}

