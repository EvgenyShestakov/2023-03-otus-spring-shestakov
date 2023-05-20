package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;
import ru.otus.library.util.Converter;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    private final OutputService outputService;

    private final Converter<Genre> converter;

    @Override
    public void save(String genreName) {
        Genre genre = new Genre(genreName);
        genreDao.save(genre);
        String stringGenre = converter.convert(genre);
        outputService.outputString(stringGenre);
    }

    @Override
    public void update(long id, String genreName) {
        Genre genre = new Genre(id, genreName);
        boolean result = genreDao.update(genre);
        String stringResult = String.valueOf(result);
        outputService.outputString(stringResult);
    }

    @Override
    public void getById(long id) {
        Optional<Genre> optionalGenre = genreDao.getById(id);
        optionalGenre.ifPresentOrElse(genre -> {
                    String stringGenre = converter.convert(genre);
                    outputService.outputString(stringGenre);
                },
                () -> outputService.outputString("There is no genre with this ID"));
    }

    @Override
    public void getAll() {
        List<Genre> genres = genreDao.getAll();
        genres.forEach(genre -> {
            String stringGenre = converter.convert(genre);
            outputService.outputString(stringGenre);
        });
        if (genres.isEmpty()) {
            outputService.outputString("Genre list is empty");
        }
    }

    @Override
    public void deleteById(long id) {
        boolean result = genreDao.deleteById(id);
        String stringResult = String.valueOf(result);
        outputService.outputString(stringResult);
    }
}
