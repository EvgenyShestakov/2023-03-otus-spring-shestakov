package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.AuthorNotFoundException;
import ru.otus.library.exception.BooksExistForAuthorException;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public Author saveAuthor(String firstName, String lastName, LocalDate dateOfBirth) {
        Author author = new Author(firstName, lastName, dateOfBirth);
        authorRepository.save(author);
        return author;
    }

    @Transactional
    @Override
    public void updateAuthor(String id, String firstName, String lastName, LocalDate dateOfBirt) {
        Author author = new Author(id, firstName, lastName, dateOfBirt);
        authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAuthorByName(String firstName, String lastName) {
        return authorRepository.getAuthorByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteAuthorById(String id) {
        Author author = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
        boolean booksExist = bookRepository.existsBooksByAuthorId(author.getId());
        if (booksExist) {
            throw new BooksExistForAuthorException();
        }
        authorRepository.delete(author);
    }
}
