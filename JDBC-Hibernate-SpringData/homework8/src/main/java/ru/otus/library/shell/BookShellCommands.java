package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Book;
import ru.otus.library.exception.AuthorNotFoundException;
import ru.otus.library.exception.BookNotFoundException;
import ru.otus.library.exception.GenreNotFoundException;
import ru.otus.library.service.BookService;
import ru.otus.library.util.Converter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class BookShellCommands {
    private final BookService bookService;

    private final Converter<Book> converter;

    @ShellMethod(value = "addBook", key = "add-b")
    public String addBook(String title, String publicationDate, String authorId, String genreId) {
        try {
            Book book = bookService.saveBook(title, LocalDate.parse(publicationDate), authorId, genreId);
            return converter.convert(book);
        } catch (AuthorNotFoundException | GenreNotFoundException e) {
            return "Missing author or genre";
        }
    }

    @ShellMethod(value = "updateBook", key = "up-b")
    public String updateBook(String id, String title, String publicationDate, String authorId, String genreId) {
        try {
            bookService.updateBook(id, title, LocalDate.parse(publicationDate), authorId, genreId);
            return String.format("Book with id %s updated", id);
        } catch (AuthorNotFoundException | GenreNotFoundException e) {
            return "Missing author or genre";
        }
    }

    @ShellMethod(value = "getBookByTitle", key = "get-b-t")
    public String getBookByTitle(String title) {
        List<Book> books = bookService.getBookByTitle(title);
        String stringBooks = converter.convert(books);
        if (stringBooks.isEmpty()) {
            stringBooks = "Book list is empty";
        }
        return stringBooks;
    }

    @ShellMethod(value = "getBook", key = "get-b")
    public String getBookById(String id) {
        try {
            Optional<Book> optionalBook = bookService.getBookById(id);
            Book book = optionalBook.orElseThrow(BookNotFoundException::new);
            return converter.convert(book);
        } catch (BookNotFoundException e) {
            return String.format("There is no book with id %s", id);
        }
    }

    @ShellMethod(value = "getBooks", key = "get-all-b")
    public String getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        String stringBooks = converter.convert(books);
        if (stringBooks.isEmpty()) {
            stringBooks = "Book list is empty";
        }
        return stringBooks;
    }

    @ShellMethod(value = "deleteBook", key = "del-b")
    public String deleteBookById(String id) {
        try {
            bookService.deleteBookById(id);
            return String.format("Book with id %s deleted", id);
        } catch (BookNotFoundException e) {
            return String.format("Book with id %s does not exist", id);
        }
    }
}
