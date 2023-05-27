package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.repositories.AuthorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public Author saveAuthor(String firstName, String lastName, LocalDate dateOfBirth) {
        Author author = new Author(firstName, lastName, dateOfBirth);
        authorRepository.saveAuthor(author);
        return author;
    }

    @Transactional
    @Override
    public boolean updateAuthor(long id, String lastName, String firstName, LocalDate dateOfBirt) {
        Author author = new Author(id, firstName, lastName, dateOfBirt);
        return authorRepository.updateAuthor(author);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAuthorByName(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        return authorRepository.getAuthorByName(author);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> getAuthorById(long id) {
        return authorRepository.getAuthorById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.getAllAuthors();
    }

    @Transactional
    @Override
    public boolean deleteAuthorById(long id) {
        return authorRepository.deleteAuthorById(id);
    }
}
