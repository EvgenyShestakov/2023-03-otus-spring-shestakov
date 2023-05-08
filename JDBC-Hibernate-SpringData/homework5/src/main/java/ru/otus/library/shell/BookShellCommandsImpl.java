package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.service.BookService;

import java.time.LocalDate;

@ShellComponent
@RequiredArgsConstructor
public class BookShellCommandsImpl implements BookShellCommands {
    private final BookService bookService;

    @ShellMethod(value = "Add book", key = "add-b")
    @Override
    public void addBook(String title, String publicationDate, long authorId, long genreId) {
        bookService.save(title, LocalDate.parse(publicationDate), authorId, genreId);
    }

    @ShellMethod(value = "updateBook", key = "up-b")
    @Override
    public void updateBook(long id, String title, String publicationDate, long authorId, long genreId) {
        bookService.update(id, title, LocalDate.parse(publicationDate), authorId, genreId);
    }

    @ShellMethod(value = "getBook", key = "get-b")
    @Override
    public void getBookById(long id) {
        bookService.getById(id);
    }

    @ShellMethod(value = "getBooks", key = "get-all-b")
    @Override
    public void getAllBooks() {
        bookService.getAll();
    }

    @ShellMethod(value = "deleteBook", key = "del-b")
    @Override
    public void deleteBookById(long id) {
        bookService.deleteById(id);
    }
}
