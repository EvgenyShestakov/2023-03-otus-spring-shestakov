package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.service.AuthorService;

import java.time.LocalDate;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShellCommandsImpl {
    private final AuthorService authorService;

    @ShellMethod(value = "Add author", key = "add-a")
    public void addAuthor(String firstName, String lastName, String dateOfBirth) {
        authorService.save(firstName, lastName, LocalDate.parse(dateOfBirth));
    }

    @ShellMethod(value = "updateAuthor", key = "up-a")
    public void updateAuthor(long id, String firstName, String lastName, String dateOfBirth) {
        authorService.update(id, firstName, lastName, LocalDate.parse(dateOfBirth));
    }

    @ShellMethod(value = "getAuthor", key = "get-a")
    public void getAuthorById(long id) {
        authorService.getById(id);
    }

    @ShellMethod(value = "getAuthors", key = "get-all-a")
    public void getAllAuthors() {
        authorService.getAll();
    }

    @ShellMethod(value = "deleteAuthor", key = "del-a")
    public void deleteAuthorById(long id) {
        authorService.deleteById(id);
    }
}
