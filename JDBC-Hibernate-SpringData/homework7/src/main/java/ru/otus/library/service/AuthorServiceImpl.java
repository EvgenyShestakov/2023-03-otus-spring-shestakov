package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.repositories.AuthorRepositoryJpa;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepositoryJpa authorRepositoryJpa;

    @Transactional
    @Override
    public Author saveAuthor(String firstName, String lastName, LocalDate dateOfBirth) {
        Author author = new Author(firstName, lastName, dateOfBirth);
        authorRepositoryJpa.save(author);
        return author;
    }

    @Transactional
    @Override
    public void updateAuthor(long id, String firstName, String lastName, LocalDate dateOfBirt) {
        Author author = new Author(id, firstName, lastName, dateOfBirt);
        authorRepositoryJpa.save(author);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAuthorByName(String firstName, String lastName) {
        return authorRepositoryJpa.getAuthorByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> getAuthorById(long id) {
        return authorRepositoryJpa.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAllAuthors() {
        return authorRepositoryJpa.findAll();
    }

    @Transactional
    @Override
    public void deleteAuthorById(long id) {
        Author author = authorRepositoryJpa.findById(id).orElseThrow(NoSuchElementException::new);
        authorRepositoryJpa.delete(author);
    }
}
