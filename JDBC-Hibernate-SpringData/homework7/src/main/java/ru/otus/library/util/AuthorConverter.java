package ru.otus.library.util;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Author;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorConverter implements Converter<Author> {
    public String convert(Author author) {
        return String.format("Author: ID = %d, firstName = %s, lastname = %s, dateOfBirth = %tF",
                author.getId(), author.getFirstName(), author.getLastName(), author.getDateOfBirth());
    }

    @Override
    public String convert(List<Author> authors) {
        return authors.stream().map(author -> String.
                format("Author: ID = %d, firstName = %s, lastname = %s, dateOfBirth = %tF%n",
                        author.getId(), author.getFirstName(), author.getLastName(),
                        author.getDateOfBirth())).collect(Collectors.joining());
    }
}
