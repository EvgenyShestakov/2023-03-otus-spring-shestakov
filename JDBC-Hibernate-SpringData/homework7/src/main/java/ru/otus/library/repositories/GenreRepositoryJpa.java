package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Genre;

public interface GenreRepositoryJpa extends JpaRepository<Genre, Long> {
}


