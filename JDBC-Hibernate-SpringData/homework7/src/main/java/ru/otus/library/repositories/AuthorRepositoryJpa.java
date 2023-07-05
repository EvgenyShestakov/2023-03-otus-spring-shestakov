package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Author;

import java.util.List;

public interface AuthorRepositoryJpa extends JpaRepository<Author, Long> {
    List<Author> getAuthorByFirstNameAndLastName(String firstName, String lastName);
}

