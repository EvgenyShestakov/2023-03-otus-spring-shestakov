package ru.otus.library.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author saveAuthor(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public void updateAuthor(Author author) {
        em.merge(author);
    }

    @Override
    public List<Author> getAuthorByName(Author author) {
        TypedQuery<Author> query = em.createQuery("select a from Author a" +
                " where a.firstName = :firstName and a.lastName = :lastName", Author.class);
        query.setParameter("firstName", author.getFirstName());
        query.setParameter("lastName", author.getLastName());
        return query.getResultList();
    }

    @Override
    public Optional<Author> getAuthorById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> getAllAuthors() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public void deleteAuthorById(Author author) {
        em.remove(author);
    }
}