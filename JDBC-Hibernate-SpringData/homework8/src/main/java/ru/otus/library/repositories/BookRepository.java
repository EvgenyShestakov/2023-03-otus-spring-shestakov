package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findBooksByTitle(String title);

    boolean existsBooksByAuthorId(String id);

    boolean existsBooksByGenreId(String id);
}
