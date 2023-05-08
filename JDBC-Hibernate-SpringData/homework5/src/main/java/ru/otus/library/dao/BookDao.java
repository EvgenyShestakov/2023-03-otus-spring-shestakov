package ru.otus.library.dao;

import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book save(Book book);

    boolean update(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    boolean deleteById(long id);
}
