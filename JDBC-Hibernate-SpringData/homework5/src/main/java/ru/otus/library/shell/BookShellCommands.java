package ru.otus.library.shell;

public interface BookShellCommands {
    void addBook(String title, String publicationDate, long authorId, long genreId);

    void updateBook(long id, String title, String publicationDate, long authorId, long genreId);

    void getBookById(long id);

    void getAllBooks();

    void deleteBookById(long id);
}
