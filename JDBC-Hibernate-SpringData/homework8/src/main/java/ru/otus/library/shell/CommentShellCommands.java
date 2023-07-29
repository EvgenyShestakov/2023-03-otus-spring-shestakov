package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Comment;
import ru.otus.library.exception.BookNotFoundException;
import ru.otus.library.exception.CommentNotFoundException;
import ru.otus.library.service.CommentService;
import ru.otus.library.util.Converter;

import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class CommentShellCommands {
    private final CommentService commentService;

    private final Converter<Comment> converter;

    @ShellMethod(value = "addComment", key = "add-c")
    public String addComment(String body, String bookId) {
        try {
            Comment comment = commentService.saveComment(body, bookId);
            return converter.convert(comment);
        } catch (BookNotFoundException e) {
            return String.format("Book with id %s does not exist", bookId);
        }
    }

    @ShellMethod(value = "updateComment", key = "up-c")
    public String updateComment(String id, String body, String bookId) {
        try {
            commentService.updateComment(id, body, bookId);
            return String.format("Comment with id %s updated", id);
        } catch (BookNotFoundException e) {
            return String.format("Book with id %s does not exist", bookId);
        }
    }

    @ShellMethod(value = "getComment", key = "get-c")
    public String getCommentById(String id) {
        try {
            Optional<Comment> optionalComment = commentService.getCommentById(id);
            Comment comment = optionalComment.orElseThrow();
            return converter.convert(comment);
        } catch (CommentNotFoundException e) {
            return String.format("There is no comment with id %s", id);
        }
    }

    @ShellMethod(value = "getCommentsByBook", key = "get-c-b")
    public String getCommentsByBook(String bookTitle) {
        List<Comment> comments = commentService.getCommentsByBook(bookTitle);
        String stringComments = converter.convert(comments);
        if (stringComments.isEmpty()) {
            stringComments = "Comment list is empty";
        }
        return stringComments;
    }

    @ShellMethod(value = "deleteComment", key = "del-c")
    public String deleteCommentById(String id) {
        try {
            commentService.deleteCommentById(id);
            return String.format("Comment with id %s deleted", id);
        } catch (CommentNotFoundException e) {
            return String.format("Comment with id %s does not exist", id);
        }
    }
}
