package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;
import ru.otus.library.util.Converter;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShellCommands {
    private final AuthorService authorService;

    private final Converter<Author> converter;

    @ShellMethod(value = "addAuthor", key = "add-a")
    public String addAuthor(String firstName, String lastName, String dateOfBirth) {
        Author author = authorService.saveAuthor(firstName, lastName, LocalDate.parse(dateOfBirth));
        return converter.convert(author);
    }

    @ShellMethod(value = "updateAuthor", key = "up-a")
    public String updateAuthor(long id, String firstName, String lastName, String dateOfBirth) {
        authorService.updateAuthor(id, firstName, lastName, LocalDate.parse(dateOfBirth));
        return String.format("Author with id %d updated", id);
    }

    @ShellMethod(value = "getAuthorByName", key = "get-a-n")
    public String getAuthorByName(String firstName, String lastName) {
        List<Author> authors = authorService.getAuthorByName(firstName, lastName);
        String stringAuthors = converter.convert(authors);
        if (authors.isEmpty()) {
            stringAuthors = "Author list is empty";
        }
        return stringAuthors;
    }

    @ShellMethod(value = "getAuthorById", key = "get-a")
    public String getAuthorById(long id) {
        try {
            Optional<Author> optionalAuthor = authorService.getAuthorById(id);
            Author author = optionalAuthor.orElseThrow();
            return converter.convert(author);
        } catch (NoSuchElementException e) {
            return "There is no author with this ID";
        }
    }

    @ShellMethod(value = "getAuthors", key = "get-all-a")
    public String getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        String stringAuthors = converter.convert(authors);
        if (authors.isEmpty()) {
            stringAuthors = "Author list is empty";
        }
        return stringAuthors;
    }

    @ShellMethod(value = "deleteAuthor", key = "del-a")
    public String deleteAuthorById(long id) {
        try {
            authorService.deleteAuthorById(id);
            return String.format("Author with id %d deleted", id);
        } catch (NoSuchElementException e) {
            return String.format("Author with id %d does not exist", id);
        }
    }
}
