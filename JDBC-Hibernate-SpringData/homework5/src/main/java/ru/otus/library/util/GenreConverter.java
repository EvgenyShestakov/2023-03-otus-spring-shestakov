package ru.otus.library.util;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Genre;

@Component
public class GenreConverter implements Converter<Genre> {
    public String convert(Genre genre) {
        return String.format("Genre: ID = %d, genreName = %s", genre.getId(), genre.getGenreName());
    }
}

