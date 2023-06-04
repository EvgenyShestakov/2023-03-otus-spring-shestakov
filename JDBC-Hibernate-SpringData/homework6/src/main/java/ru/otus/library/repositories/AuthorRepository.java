package ru.otus.library.repositories;

import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author saveAuthor(Author author);

    void updateAuthor(Author author);

    List<Author> getAuthorByName(Author author);

    Optional<Author> getAuthorById(long id);

    List<Author> getAllAuthors();

    void deleteAuthorById(Author author);
}

