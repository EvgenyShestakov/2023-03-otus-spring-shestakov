package ru.otus.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.configuration.TestConfiguration;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.BookRequestDto;
import ru.otus.library.dto.CommentRequestDto;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;
import ru.otus.library.service.GenreService;
import ru.otus.library.util.MapperDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@ComponentScan(basePackages = "ru.otus.library.controller")
@ContextConfiguration(classes = TestConfiguration.class)
class CommentControllerTest {
    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private MapperDto mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetComments() throws Exception {
        Book book = new Book();
        book.setTitle("It");
        given(bookService.getBookById(anyLong())).willReturn(Optional.of(book));
        given(mapper.convertFromBookToBookDto(book)).willReturn(new BookRequestDto());
        List<Comment> comments = Arrays.asList(new Comment("Comment 1", book), new Comment("Comment 2", book));
        given(commentService.getCommentsByBook(book)).willReturn(comments);
        mockMvc.perform(get("/comments/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/show-comment"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("comment"));
    }

    @Test
    void testCreateCommentValidInput() throws Exception {
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setBookId(1L);
        commentRequestDto.setBody("Cool");
        mockMvc.perform(post("/comments")
                        .flashAttr("comment", commentRequestDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/comments/{bookId}"));
    }

    @Test
    void testCreateCommentInvalidInput() throws Exception {
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setBookId(1L);
        Book book = new Book();
        book.setTitle("It");
        given(bookService.getBookById(anyLong())).willReturn(Optional.of(book));
        given(mapper.convertFromBookToBookDto(book)).willReturn(new BookRequestDto());
        mockMvc.perform(post("/comments")
                        .flashAttr("comment", commentRequestDto))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/show-comment"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("comment"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("comment", "body"));
    }
}
