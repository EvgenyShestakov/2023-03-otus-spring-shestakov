package ru.otus.library.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.library.domain.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class AuthorRepositoryJpaTest {
    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Test
    void mustBeGetAllAuthors() {
        String expectedName = "Maria Garcia";
        List<Author> authors = authorRepositoryJpa.getAuthorByFirstNameAndLastName("Maria", "Garcia");
        Author author = authors.get(0);
        String actualName = author.getFirstName() + " " + author.getLastName();
        assertEquals(expectedName, actualName);
    }
}
