package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.GenreService;
import ru.otus.library.util.Converter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class GenreShellCommands {
    private final GenreService genreService;

    private final Converter<Genre> converter;

    @ShellMethod(value = "addGenre", key = "add-g")
    public String addGenre(String genreName) {
        Genre genre = genreService.saveGenre(genreName);
        return converter.convert(genre);
    }

    @ShellMethod(value = "updateGenre", key = "up-g")
    public String updateGenre(long id, String genreName) {
        boolean result = genreService.updateGenre(id, genreName);
        return String.valueOf(result);
    }

    @ShellMethod(value = "getGenre", key = "get-g")
    public String getGenreById(long id) {
        try {
            Optional<Genre> optionalGenre = genreService.getGenreById(id);
            Genre genre = optionalGenre.orElseThrow();
            return converter.convert(genre);
        } catch (NoSuchElementException e) {
            return "There is no genre with this ID";
        }
    }

    @ShellMethod(value = "getGenres", key = "get-all-g")
    public String getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        String stringGenres = converter.convert(genres);
        if (genres.isEmpty()) {
            stringGenres = "Genre list is empty";
        }
        return stringGenres;
    }

    @ShellMethod(value = "deleteGenre", key = "del-g")
    public String deleteGenreById(long id) {
        boolean result = genreService.deleteGenreById(id);
        return String.valueOf(result);
    }
}
