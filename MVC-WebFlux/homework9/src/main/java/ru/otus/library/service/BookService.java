package ru.otus.library.service;

import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookRequestDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(BookRequestDto bookRequestDto);

    void updateBook(BookRequestDto bookRequestDto);

    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();

    void deleteBookById(long id);
}


