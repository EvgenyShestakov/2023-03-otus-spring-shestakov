package ru.otus.library.util;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentConverter implements Converter<Comment> {
    @Override
    public String convert(Comment comment) {
        return String.format("Comment: ID = %s, body = %s", comment.getId(), comment.getBody());
    }

    @Override
    public String convert(List<Comment> comments) {
        return comments.stream().map(comment -> String.format("Comment: ID = %s, body = %s%n",
                        comment.getId(), comment.getBody())).collect(Collectors.joining());
    }
}
