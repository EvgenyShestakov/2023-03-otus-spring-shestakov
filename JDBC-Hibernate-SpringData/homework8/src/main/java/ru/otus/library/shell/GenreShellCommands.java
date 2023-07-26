package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.BooksExistForGenreException;
import ru.otus.library.exception.GenreNotFoundException;
import ru.otus.library.service.GenreService;
import ru.otus.library.util.Converter;

import java.util.List;
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
    public String updateGenre(String id, String genreName) {
        genreService.updateGenre(id, genreName);
        return String.format("Genre with id %s updated", id);
    }

    @ShellMethod(value = "getGenre", key = "get-g")
    public String getGenreById(String id) {
        try {
            Optional<Genre> optionalGenre = genreService.getGenreById(id);
            Genre genre = optionalGenre.orElseThrow(GenreNotFoundException::new);
            return converter.convert(genre);
        } catch (GenreNotFoundException e) {
            return String.format("There is no genre with id %s", id);
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
    public String deleteGenreById(String id) {
        try {
            genreService.deleteGenreById(id);
            return String.format("Genre with id %s deleted", id);
        } catch (GenreNotFoundException e) {
            return String.format("Genre with id %s does not exist", id);
        } catch (BooksExistForGenreException e) {
            return String.format("The genre with id %s has books. Remove them first.", id);
        }
    }
}
