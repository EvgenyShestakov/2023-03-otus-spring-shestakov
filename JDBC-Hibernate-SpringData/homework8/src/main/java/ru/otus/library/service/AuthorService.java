package ru.otus.library.service;

import ru.otus.library.domain.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author saveAuthor(String firstName, String lastName, LocalDate dateOfBirth);

    void updateAuthor(String id, String firstName, String lastName, LocalDate dateOfBirth);

    List<Author> getAuthorByName(String firstName, String lastName);

    Optional<Author> getAuthorById(String id);

    List<Author> getAllAuthors();

    void deleteAuthorById(String id);
}
