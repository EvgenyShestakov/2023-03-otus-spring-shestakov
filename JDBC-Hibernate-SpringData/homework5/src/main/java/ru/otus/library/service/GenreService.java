package ru.otus.library.service;

public interface GenreService {
    void save(String genreName);

    void update(long id, String genreName);

    void getById(long id);

    void getAll();

    void deleteById(long id);
}
