package ru.otus.library.service;

import ru.otus.library.domain.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(String title, LocalDate publicationDate, long authorId, long genreId);

    boolean updateBook(long id, String title, LocalDate publicationDate, long authorId, long genreId);

    List<Book> getBookByTitle(String title);

    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();

    boolean deleteBookById(long id);
}


