package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.dao.BookDao;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

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

    @Override
    public void save(String title, LocalDate publicationDate, long authorId, long genreId) {
        Optional<Author> author = authorDao.getById(authorId);
        Optional<Genre> genre = genreDao.getById(genreId);
        if (author.isPresent() && genre.isPresent()) {
            Book book = new Book(title, publicationDate, author.get(), genre.get());
            outputService.outputString(bookDao.save(book).toString());
        } else {
            if (author.isEmpty()) {
                outputService.outputString(String.format("Author with ID = %d not found", authorId));
            }
            if (genre.isEmpty()) {
                outputService.outputString(String.format("Genre with ID = %d not found", genreId));
            }
        }
    }

    @Override
    public void update(long id, String title, LocalDate publicationDate, long authorId, long genreId) {
        Optional<Author> author = authorDao.getById(authorId);
        Optional<Genre> genre = genreDao.getById(genreId);
        if (author.isPresent() && genre.isPresent()) {
            Book book = new Book(id, title, publicationDate, author.get(), genre.get());
            outputService.outputString(String.valueOf(bookDao.update(book)));
        } else {
            if (author.isEmpty()) {
                outputService.outputString(String.format("Author with ID = %d not found", authorId));
            }
            if (genre.isEmpty()) {
                outputService.outputString(String.format("Genre with ID = %d not found", genreId));
            }
        }
    }

    @Override
    public void getById(long id) {
        Optional<Book> optionalBook = bookDao.getById(id);
        optionalBook.ifPresentOrElse(book -> outputService.outputString(book.toString()),
                () -> outputService.outputString("There is no book with this ID"));
    }

    @Override
    public void getAll() {
        List<Book> books = bookDao.getAll();
        books.forEach(book -> outputService.outputString(book.toString()));
        if (books.isEmpty()) {
            outputService.outputString("Book list is empty");
        }
    }

    @Override
    public void deleteById(long id) {
        outputService.outputString(String.valueOf(bookDao.deleteById(id)));
    }
}
