package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.dao.BookDao;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.util.Converter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    private final OutputService outputService;

    private final Converter<Book> converter;

    @Override
    public void save(String title, LocalDate publicationDate, long authorId, long genreId) {
        Optional<Author> author = authorDao.getById(authorId);
        Optional<Genre> genre = genreDao.getById(genreId);
        if (validate(author, genre, authorId, genreId)) {
            Book book = new Book(title, publicationDate, author.get(), genre.get());
            bookDao.save(book);
            String stringBook = converter.convert(book);
            outputService.outputString(stringBook);
        }
    }

    @Override
    public void update(long id, String title, LocalDate publicationDate, long authorId, long genreId) {
        Optional<Author> author = authorDao.getById(authorId);
        Optional<Genre> genre = genreDao.getById(genreId);
        if (validate(author, genre, authorId, genreId)) {
            Book book = new Book(id, title, publicationDate, author.get(), genre.get());
            boolean result = bookDao.update(book);
            String stringResult = String.valueOf(result);
            outputService.outputString(stringResult);
        }
    }

    @Override
    public void getById(long id) {
        Optional<Book> optionalBook = bookDao.getById(id);
        optionalBook.ifPresentOrElse(book -> {
                    String stringBook = converter.convert(book);
                    outputService.outputString(stringBook);
                },
                () -> outputService.outputString("There is no book with this ID"));
    }

    @Override
    public void getAll() {
        List<Book> books = bookDao.getAll();
        books.forEach(book -> {
            String stringBook = converter.convert(book);
            outputService.outputString(stringBook);
        });
        if (books.isEmpty()) {
            outputService.outputString("Book list is empty");
        }
    }

    @Override
    public void deleteById(long id) {
        boolean result = bookDao.deleteById(id);
        String stringResult = String.valueOf(result);
        outputService.outputString(stringResult);
    }

    private boolean validate(Optional<Author> author,
                             Optional<Genre> genre, long authorId, long genreId) {
        boolean isValid = author.isPresent() && genre.isPresent();
        if (author.isEmpty()) {
            String authorMessage = String.format("Author with ID = %d not found", authorId);
            outputService.outputString(authorMessage);
        }
        if (genre.isEmpty()) {
            String genreMessage = String.format("Genre with ID = %d not found", genreId);
            outputService.outputString(genreMessage);
        }
        return isValid;
    }
}
