package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Comment;
import ru.otus.library.service.CommentService;
import ru.otus.library.util.Converter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class CommentShellCommands {
    private final CommentService commentService;

    private final Converter<Comment> converter;

    @ShellMethod(value = "addComment", key = "add-c")
    public String addComment(String body, long bookId) {
        try {
            Comment comment = commentService.saveComment(body, bookId);
            return converter.convert(comment);
        } catch (NoSuchElementException e) {
            return "Missing book";
        }
    }

    @ShellMethod(value = "updateComment", key = "up-c")
    public String updateComment(long id, String body, long bookId) {
        try {
            commentService.updateComment(id, body, bookId);
            return String.format("Comment with id %d updated", id);
        } catch (NoSuchElementException e) {
            return "Missing book";
        }
    }

    @ShellMethod(value = "getComment", key = "get-c")
    public String getCommentById(long id) {
        try {
            Optional<Comment> optionalComment = commentService.getCommentById(id);
            Comment comment = optionalComment.orElseThrow();
            return converter.convert(comment);
        } catch (NoSuchElementException e) {
            return "There is no comment with this ID";
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
    public String deleteCommentById(long id) {
        try {
            commentService.deleteCommentById(id);
            return String.format("Comment with id %d deleted", id);
        } catch (NoSuchElementException e) {
            return String.format("Comment with id %d does not exist", id);
        }
    }
}
