package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Book;
import ru.otus.library.service.BookService;
import ru.otus.library.util.Converter;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class BookShellCommands {
    private final BookService bookService;

    private final Converter<Book> converter;

    @ShellMethod(value = "addBook", key = "add-b")
    public String addBook(String title, String publicationDate, long authorId, long genreId) {
        try {
            Book book = bookService.saveBook(title, LocalDate.parse(publicationDate), authorId, genreId);
            return converter.convert(book);
        } catch (NoSuchElementException e) {
            return "Missing author or genre";
        }
    }

    @ShellMethod(value = "updateBook", key = "up-b")
    public String updateBook(long id, String title, String publicationDate, long authorId, long genreId) {
        try {
            boolean result = bookService.updateBook(id, title, LocalDate.parse(publicationDate), authorId, genreId);
            return String.valueOf(result);
        } catch (NoSuchElementException e) {
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
    public String getBookById(long id) {
        try {
            Optional<Book> optionalBook = bookService.getBookById(id);
            Book book = optionalBook.orElseThrow();
            return converter.convert(book);
        } catch (NoSuchElementException e) {
            return "There is no book with this ID";
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
    public String deleteBookById(long id) {
        boolean result = bookService.deleteBookById(id);
        return String.valueOf(result);
    }
}
