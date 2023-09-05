package ru.otus.library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.BookRequestDto;
import ru.otus.library.dto.CommentRequestDto;
import ru.otus.library.exeption.NotFoundException;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;
import ru.otus.library.util.MapperDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    private final BookService bookService;

    private final MapperDto mapper;

    @GetMapping("comments/{id}")
    public String getComments(@PathVariable long id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        List<Comment> comments = commentService.getCommentsByBook(book);
        BookRequestDto bookRequestDto = mapper.convertFromBookToBookDto(book);
        model.addAttribute("book", bookRequestDto);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new CommentRequestDto());
        return "comment/show-comment";
    }

    @PostMapping("comments")
    public String createComment(@ModelAttribute("comment") @Valid CommentRequestDto commentRequestDto,
                                BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Book book = bookService.getBookById(commentRequestDto.getBookId()).
                    orElseThrow(() -> new NotFoundException("Book not found"));
            List<Comment> comments = commentService.getCommentsByBook(book);
            BookRequestDto bookRequestDto = mapper.convertFromBookToBookDto(book);
            List<CommentRequestDto> commentRequestDtos = mapper.convertFromCommentToCommentDto(comments);
            model.addAttribute("book", bookRequestDto);
            model.addAttribute("comments", commentRequestDtos);
            return "comment/show-comment";
        }
        commentService.saveComment(commentRequestDto);
        redirectAttributes.addFlashAttribute("bookId", commentRequestDto.getBookId());
        return "redirect:/comments/" + commentRequestDto.getBookId();
    }
}
