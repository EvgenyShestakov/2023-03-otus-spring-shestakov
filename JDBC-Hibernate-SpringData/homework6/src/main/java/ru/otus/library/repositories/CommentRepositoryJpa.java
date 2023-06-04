package ru.otus.library.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Comment saveComment(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void updateComment(Comment comment) {
        em.merge(comment);
    }

    @Override
    public Optional<Comment> getCommentById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("comment-book-entity-graph");
        return Optional.ofNullable(em.find(Comment.class, id,
                Collections.singletonMap("javax.persistence.fetchgraph", entityGraph)));
    }

    @Override
    public List<Comment> getCommentsByBook(List<Book> books) {
        List<Long> bookIds = books.stream().map(Book::getId).collect(Collectors.toList());
        TypedQuery<Comment> query = em.
                createQuery("select c from Comment c where c.book.id in(:bookIds)", Comment.class);
        query.setParameter("bookIds", bookIds);
        return query.getResultList();
    }

    @Override
    public void deleteCommentById(Comment comment) {
        em.remove(comment);
    }
}
