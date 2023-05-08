package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    private final OutputService outputService;

    @Override
    public void save(String firstName, String lastName, LocalDate dateOfBirth) {
        Author author = new Author(firstName, lastName, dateOfBirth);
        outputService.outputString(authorDao.save(author).toString());
    }

    @Override
    public void update(long id, String lastName, String firstName, LocalDate dateOfBirth) {
        Author author = new Author(id, firstName, lastName, dateOfBirth);
        boolean result = authorDao.update(author);
        outputService.outputString(String.valueOf(result));
    }

    @Override
    public void getById(long id) {
        Optional<Author> optionalAuthor = authorDao.getById(id);
        optionalAuthor.ifPresentOrElse(
                author -> outputService.outputString(author.toString()),
                () -> outputService.outputString("There is no author with this ID")
        );
    }

    @Override
    public void getAll() {
        List<Author> authors = authorDao.getAll();
        authors.forEach(author -> outputService.outputString(author.toString()));
        if (authors.isEmpty()) {
            outputService.outputString("Author list is empty");
        }
    }

    @Override
    public void deleteById(long id) {
        outputService.outputString(String.valueOf(authorDao.deleteById(id)));
    }
}
