package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;
import ru.otus.library.util.Converter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    private final OutputService outputService;

    private final Converter<Author> converter;

    @Override
    public void save(String firstName, String lastName, LocalDate dateOfBirth) {
        Author author = new Author(firstName, lastName, dateOfBirth);
        authorDao.save(author);
        String stringAuthor = converter.convert(author);
        outputService.outputString(stringAuthor);
    }

    @Override
    public void update(long id, String lastName, String firstName, LocalDate dateOfBirth) {
        Author author = new Author(id, firstName, lastName, dateOfBirth);
        boolean result = authorDao.update(author);
        String stringResult = String.valueOf(result);
        outputService.outputString(stringResult);
    }

    @Override
    public void getById(long id) {
        Optional<Author> optionalAuthor = authorDao.getById(id);
        optionalAuthor.ifPresentOrElse(author -> {
                    String stringAuthor = converter.convert(author);
                    outputService.outputString(stringAuthor);
                },
                () -> outputService.outputString("There is no author with this ID"));
    }

    @Override
    public void getAll() {
        List<Author> authors = authorDao.getAll();
        authors.forEach(author -> {
            String stringAuthor = converter.convert(author);
            outputService.outputString(stringAuthor);
        });
        if (authors.isEmpty()) {
            outputService.outputString("Author list is empty");
        }
    }

    @Override
    public void deleteById(long id) {
        boolean result = authorDao.deleteById(id);
        String stringResult = String.valueOf(result);
        outputService.outputString(stringResult);
    }
}
