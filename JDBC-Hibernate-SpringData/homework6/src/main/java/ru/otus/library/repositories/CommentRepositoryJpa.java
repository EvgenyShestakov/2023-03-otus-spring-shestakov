package ru.otus.library.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

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
    public boolean updateComment(Comment comment) {
        Query query = em.
                createQuery("update Comment c set c.body = :body where c.id = :id");
        query.setParameter("body", comment.getBody());
        query.setParameter("id", comment.getId());
        int rowsAffected = query.executeUpdate();
        return rowsAffected > 0;
    }

    @Override
    public Optional<Comment> getCommentById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("comment-book-entity-graph");
        TypedQuery<Comment> query = em.
                createQuery("select c from Comment c where c.id = :id", Comment.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Comment> comments = query.getResultList();
        return comments.isEmpty() ? Optional.empty() : Optional.of(comments.get(0));
    }

    @Override
    public List<Comment> getCommentsByBook(List<Book> books) {
        EntityGraph<?> entityGraph = em.getEntityGraph("comment-book-entity-graph");
        List<Long> bookIds = books.stream().map(Book::getId).collect(Collectors.toList());
        TypedQuery<Comment> query = em.
                createQuery("select c from Comment c where c.book.id in(:bookIds)", Comment.class);
        query.setParameter("bookIds", bookIds);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public boolean deleteCommentById(long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        int rowsAffected = query.executeUpdate();
        return rowsAffected > 0;
    }
}
