package ru.otus.library.shell;

public interface GenreShellCommands {
    void addGenre(String genreName);

    void updateGenre(long id, String genreName);

    void getGenreById(long id);

    void getAllGenres();

    void deleteGenreById(long id);
}
