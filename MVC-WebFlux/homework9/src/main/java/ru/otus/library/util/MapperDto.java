package ru.otus.library.util;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.BookRequestDto;
import ru.otus.library.dto.CommentRequestDto;

import java.util.List;

@Component
public class MapperDto {
    public BookRequestDto convertFromBookToBookDto(Book book) {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setBookId(book.getId());
        bookRequestDto.setTitle(book.getTitle());
        bookRequestDto.setAuthorId(book.getAuthor().getId());
        bookRequestDto.setGenreId(book.getGenre().getId());
        return bookRequestDto;
    }

    public List<CommentRequestDto> convertFromCommentToCommentDto(List<Comment> comments) {
        return comments.stream().map(comment -> {
            CommentRequestDto commentRequestDto = new CommentRequestDto();
            commentRequestDto.setBookId(comment.getBook().getId());
            commentRequestDto.setBody(comment.getBody());
            return commentRequestDto;
        }).toList();
    }
}
