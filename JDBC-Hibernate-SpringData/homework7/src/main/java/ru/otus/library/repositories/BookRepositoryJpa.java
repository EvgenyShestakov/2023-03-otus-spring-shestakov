package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa extends JpaRepository<Book, Long> {
    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findBooksByTitle(String title);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findById(@NonNull Long id);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();
}
