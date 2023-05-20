package ru.otus.library.service;

import java.time.LocalDate;

public interface BookService {
    void save(String title, LocalDate publicationDate, long authorId, long genreId);

    void update(long id, String title, LocalDate publicationDate, long authorId, long genreId);

    void getById(long id);

    void getAll();

    void deleteById(long id);
}
