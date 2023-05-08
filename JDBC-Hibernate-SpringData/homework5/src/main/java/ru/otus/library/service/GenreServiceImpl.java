package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    private final OutputService outputService;

    @Override
    public void save(String genreName) {
        Genre genre = new Genre(genreName);
        outputService.outputString(genreDao.save(genre).toString());
    }

    @Override
    public void update(long id, String genreName) {
        Genre genre = new Genre(id, genreName);
        boolean result = genreDao.update(genre);
        outputService.outputString(String.valueOf(result));
    }

    @Override
    public void getById(long id) {
        Optional<Genre> optionalGenre = genreDao.getById(id);
        optionalGenre.ifPresentOrElse(genre -> outputService.outputString(genre.toString()),
                () -> outputService.outputString("There is no genre with this ID"));
    }

    @Override
    public void getAll() {
        List<Genre> genres = genreDao.getAll();
        genres.forEach(genre -> outputService.outputString(genre.toString()));
        if (genres.isEmpty()) {
            outputService.outputString("Genre list is empty");
        }
    }

    @Override
    public void deleteById(long id) {
        outputService.outputString(String.valueOf(genreDao.deleteById(id)));
    }
}
