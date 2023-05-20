package ru.otus.library.util;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Book;

@Component
public class BookConverter implements Converter<Book> {
    public String convert(Book book) {
        return String.format("Book: ID = %d, title = %s, publicationDate = %tF," +
                        " authorFirstName = %s, authorLastName = %s, genreName = %s", book.getId(),
                book.getTitle(), book.getPublicationDate(), book.getAuthor().getFirstName(),
                book.getAuthor().getLastName(), book.getGenre().getGenreName());
    }
}


