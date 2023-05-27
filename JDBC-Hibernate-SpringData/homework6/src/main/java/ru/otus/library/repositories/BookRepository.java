package ru.otus.library.repositories;

import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book saveBook(Book book);

    boolean updateBook(Book book);

    List<Book> getBookByTitle(String bookTitle);

    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();

    boolean deleteBookById(long id);
}
