package ru.otus.library.shell;

public interface AuthorShellCommands {
    void addAuthor(String firstName, String lastName, String dateOfBirth);

    void updateAuthor(long id, String firstName, String lastName, String dateOfBirth);

    void getAuthorById(long id);

    void getAllAuthors();

    void deleteAuthorById(long id);
}
