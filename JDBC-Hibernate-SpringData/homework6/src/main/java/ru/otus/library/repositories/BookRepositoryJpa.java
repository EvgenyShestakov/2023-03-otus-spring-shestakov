package ru.otus.library.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book saveBook(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void updateBook(Book book) {
        em.merge(book);
    }

    @Override
    public List<Book> getBookByTitle(String title) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title = :title", Book.class);
        query.setParameter("title", title);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        return Optional.ofNullable(em.find(Book.class, id,
                Collections.singletonMap("javax.persistence.fetchgraph", entityGraph)));
    }

    @Override
    public List<Book> getAllBooks() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteBookById(Book book) {
        em.remove(book);
    }
}
