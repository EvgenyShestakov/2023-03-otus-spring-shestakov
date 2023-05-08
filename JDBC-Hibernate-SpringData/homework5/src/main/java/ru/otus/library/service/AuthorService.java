package ru.otus.library.service;

import java.time.LocalDate;

public interface AuthorService {
    void save(String firstName, String lastName, LocalDate dateOfBirth);

    void update(long id, String firstName, String lastName, LocalDate dateOfBirth);

    void getById(long id);

    void getAll();

    void deleteById(long id);
}
