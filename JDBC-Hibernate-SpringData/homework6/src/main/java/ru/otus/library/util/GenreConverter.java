package ru.otus.library.util;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreConverter implements Converter<Genre> {
    public String convert(Genre genre) {
        return String.format("Genre: ID = %d, genreName = %s", genre.getId(), genre.getGenreName());
    }

    @Override
    public String convert(List<Genre> genres) {
        return genres.stream().map(genre -> String.format("Genre: ID = %d, genreName = %s%n",
                genre.getId(), genre.getGenreName())).collect(Collectors.joining());
    }
}

