package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.service.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class GenreShellCommandsImpl implements GenreShellCommands {
    private final GenreService genreService;

    @ShellMethod(value = "Add genre", key = "add-g")
    @Override
    public void addGenre(String genreName) {
        genreService.save(genreName);
    }

    @ShellMethod(value = "updateGenre", key = "up-g")
    @Override
    public void updateGenre(long id, String genreName) {
        genreService.update(id, genreName);
    }

    @ShellMethod(value = "getGenre", key = "get-g")
    @Override
    public void getGenreById(long id) {
        genreService.getById(id);
    }

    @ShellMethod(value = "getGenres", key = "get-all-g")
    @Override
    public void getAllGenres() {
        genreService.getAll();
    }

    @ShellMethod(value = "deleteGenre", key = "del-g")
    @Override
    public void deleteGenreById(long id) {
        genreService.deleteById(id);
    }
}
