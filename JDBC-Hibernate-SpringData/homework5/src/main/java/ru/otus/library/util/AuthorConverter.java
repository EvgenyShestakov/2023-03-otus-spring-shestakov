package ru.otus.library.util;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Author;

@Component
public class AuthorConverter implements Converter<Author> {
    public String convert(Author author) {
        return String.format("Author: ID = %d, firstName = %s, lastname = %s, dateOfBirth = %tF",
                author.getId(), author.getFirstName(), author.getLastName(), author.getDateOfBirth());
    }
}
