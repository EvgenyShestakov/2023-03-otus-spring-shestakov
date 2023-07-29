package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.BooksExistForGenreException;
import ru.otus.library.exception.GenreNotFoundException;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public Genre saveGenre(String genreName) {
        Genre genre = new Genre(null, genreName);
        return genreRepository.save( genre);
    }

    @Transactional
    @Override
    public void updateGenre(String id, String genreName) {
        Genre genre = new Genre(id, genreName);
        genreRepository.save(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getGenreById(String id) {
        return genreRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteGenreById(String id) {
        Genre genre = genreRepository.findById(id).orElseThrow(GenreNotFoundException::new);
        boolean booksExist = bookRepository.existsBooksByGenreId(genre.getId());
        if (booksExist) {
            throw new BooksExistForGenreException();
        }
        genreRepository.delete(genre);
    }
}
