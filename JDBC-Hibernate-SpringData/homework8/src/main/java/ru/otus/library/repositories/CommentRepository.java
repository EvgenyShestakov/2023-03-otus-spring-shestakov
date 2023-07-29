package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findCommentsByBookIn(List<Book> books);

    void deleteAllCommentsByBookId(String id);
}
