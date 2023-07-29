package ru.otus.library.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.library.domain.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void mustBeGetAllAuthors() {
        String expectedName = "Maria Garcia";
        List<Author> authors = authorRepository.getAuthorByFirstNameAndLastName("Maria", "Garcia");
        Author author = authors.get(0);
        String actualName = author.getFirstName() + " " + author.getLastName();
        assertEquals(expectedName, actualName);
    }
}
