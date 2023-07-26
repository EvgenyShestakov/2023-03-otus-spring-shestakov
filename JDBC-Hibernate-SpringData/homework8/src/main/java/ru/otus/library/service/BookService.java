package ru.otus.library.service;

import ru.otus.library.domain.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(String title, LocalDate publicationDate, String authorId, String genreId);

    void updateBook(String id, String title, LocalDate publicationDate, String authorId, String genreId);

    List<Book> getBookByTitle(String title);

    Optional<Book> getBookById(String id);

    List<Book> getAllBooks();

    void deleteBookById(String id);
}


