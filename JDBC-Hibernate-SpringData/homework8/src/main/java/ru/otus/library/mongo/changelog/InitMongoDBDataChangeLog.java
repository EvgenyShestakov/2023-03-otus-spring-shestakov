package ru.otus.library.mongo.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.CommentRepository;
import ru.otus.library.repositories.GenreRepository;

import java.time.LocalDate;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
    @ChangeSet(order = "000", id = "dropDB", author = "ev_schestakov", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "01", id = "initAuthors", author = "ev_schestakov", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        List<Author> authors = List.of(
                new Author("1", "John", "Smith", LocalDate.of(1990, 1, 1)),
                new Author("2", "Emily", "Johnson", LocalDate.of(1985, 2, 15)),
                new Author("3", "Michael", "Lee", LocalDate.of(1995, 5, 10)),
                new Author("4", "Maria", "Garcia", LocalDate.of(1988, 12, 20)),
                new Author("5", "Wei", "Wang", LocalDate.of(1993, 7, 3)),
                new Author("6", "Chan", "Tai", LocalDate.of(1985, 3, 6)));
        authorRepository.saveAll(authors);
    }

    @ChangeSet(order = "02", id = "initGenres", author = "ev_schestakov", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        List<Genre> genres = List.of(
                new Genre("1", "Mystic"),
                new Genre("2", "Horror"),
                new Genre("3", "Fantastic"),
                new Genre("4", "Comedy"),
                new Genre("5", "Drama"),
                new Genre("6", "Action"));
        genreRepository.saveAll(genres);
    }

    @ChangeSet(order = "03", id = "initBooks", author = "ev_schestakov", runAlways = true)
    public void initBooks(BookRepository bookRepository) {
        List<Book> books = List.of(
                new Book("1", "The Haunting of Hill House",
                        LocalDate.of(1959, 10, 16),
                        new Author("5", "Wei", "Wang",
                                LocalDate.of(1993, 7, 3)),
                        new Genre("2", "Horror"))
                , new Book("2", "It", LocalDate.of(1986, 9, 15),
                        new Author("4", "Maria", "Garcia",
                                LocalDate.of(1988, 12, 20)),
                        new Genre("2", "Horror"))
                , new Book("3", "The Lord of the Rings",
                        LocalDate.of(1954, 7, 29),
                        new Author("1", "John", "Smith",
                                LocalDate.of(1990, 1, 1)),
                        new Genre("3", "Fantastic")),
                new Book("4", "Bridget Jones’s Diary",
                        LocalDate.of(1996, 4, 1),
                        new Author("2", "Emily", "Johnson",
                                LocalDate.of(1985, 2, 15)),
                        new Genre("4", "Comedy")),
                new Book("5", "The Catcher in the Rye",
                        LocalDate.of(1951, 7, 16),
                        new Author("3", "Michael", "Lee",
                                LocalDate.of(1995, 5, 10)),
                        new Genre("5", "Drama")),
                new Book("6", "Mu-mu", LocalDate.of(1958, 9, 16),
                        new Author("3", "Michael", "Lee",
                                LocalDate.of(1995, 5, 10)),
                        new Genre("4", "Comedy")));
        bookRepository.saveAll(books);
    }

    @ChangeSet(order = "04", id = "initComments", author = "admin", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
        List<Comment> comments = List.of(new Comment("1", "Wonderful book",
                        new Book("1", "The Haunting of Hill House",
                                LocalDate.of(1959, 10, 16),
                                new Author("5", "Wei", "Wang",
                                        LocalDate.of(1993, 7, 3)),
                                new Genre("2", "Horror"))),
                new Comment("2", "Don't read it", new Book("2", "It",
                        LocalDate.of(1986, 9, 15),
                        new Author("4", "Maria", "Garcia",
                                LocalDate.of(1988, 12, 20)),
                        new Genre("2", "Horror"))),
                new Comment("3", "I fell asleep while reading",
                        new Book("3", "The Lord of the Rings",
                                LocalDate.of(1954, 7, 29),
                                new Author("1", "John", "Smith",
                                        LocalDate.of(1990, 1, 1)),
                                new Genre("3", "Fantastic"))),
                new Comment("4", "Not interested", new Book("4", "Bridget Jones’s Diary",
                        LocalDate.of(1996, 4, 1),
                        new Author("2", "Emily", "Johnson",
                                LocalDate.of(1985, 2, 15)),
                        new Genre("4", "Comedy"))),
                new Comment("5", "Best book", new Book("5", "The Catcher in the Rye",
                        LocalDate.of(1951, 7, 16),
                        new Author("3", "Michael", "Lee",
                                LocalDate.of(1995, 5, 10)),
                        new Genre("5", "Drama"))));
        commentRepository.saveAll(comments);
    }
}
