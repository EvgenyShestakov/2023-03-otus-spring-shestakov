package ru.otus.library.util;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Book;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter implements Converter<Book> {
    public String convert(Book book) {
        return String.format("Book: ID = %d, title = %s, publicationDate = %tF," +
                        " authorFirstName = %s, authorLastName = %s, genreName = %s", book.getId(),
                book.getTitle(), book.getPublicationDate(), book.getAuthor().getFirstName(),
                book.getAuthor().getLastName(), book.getGenre().getGenreName());
    }

    @Override
    public String convert(List<Book> books) {
        return books.stream().map(book -> String.format("Book: ID = %d, title = %s, publicationDate = %tF," +
                        " authorFirstName = %s, authorLastName = %s, genreName = %s%n", book.getId(),
                book.getTitle(), book.getPublicationDate(), book.getAuthor().getFirstName(),
                book.getAuthor().getLastName(), book.getGenre().getGenreName())).collect(Collectors.joining());
    }
}


