package ru.otus.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.configuration.TestConfiguration;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookRequestDto;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;
import ru.otus.library.service.GenreService;
import ru.otus.library.util.MapperDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ComponentScan(basePackages = "ru.otus.library.controller")
@ContextConfiguration(classes = TestConfiguration.class)
class BookControllerTest {
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
    void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", new Author(), new Genre()),
                new Book(2L, "Book 2", new Author(), new Genre())
        );
        given(bookService.getAllBooks()).willReturn(books);
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(2)));
    }

    @Test
    void testGoToCreateBook() throws Exception {
        List<Author> authors = Arrays.asList(new Author(), new Author());
        List<Genre> genres = Arrays.asList(new Genre(), new Genre());
        given(authorService.getAllAuthors()).willReturn(authors);
        given(genreService.getAllGenres()).willReturn(genres);

        mockMvc.perform(get("/books/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/create"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("authors", hasSize(2)))
                .andExpect(model().attribute("genres", hasSize(2)));
    }

    @Test
    void testCreateBook() throws Exception {
        mockMvc.perform(post("/books/create")
                        .param("title", "Book Title")
                        .param("authorId", "1")
                        .param("genreId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    void testCreateBookInvalidInput() throws Exception {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("");
        mockMvc.perform(post("/books/create")
                        .flashAttr("book", bookRequestDto))
                .andExpect(status().isOk())
                .andExpect(view().name("book/create"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("book", "title"));
    }

    @Test
    void testGoToEditBook() throws Exception {
        Book book = new Book();
        book.setTitle("Sample Title");
        given(bookService.getBookById(anyLong())).willReturn(Optional.of(book));
        given(mapper.convertFromBookToBookDto(book)).willReturn(new BookRequestDto());
        List<Author> authors = Arrays.asList(new Author(), new Author());
        List<Genre> genres = Arrays.asList(new Genre(), new Genre());
        given(authorService.getAllAuthors()).willReturn(authors);
        given(genreService.getAllGenres()).willReturn(genres);
        mockMvc.perform(get("/books/edit/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"));
    }

    @Test
    void testEditBook() throws Exception {
        mockMvc.perform(post("/books/edit")
                        .param("id", "1")
                        .param("title", "Updated Title")
                        .param("authorId", "1")
                        .param("genreId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    void testEditBookInvalidInput() throws Exception {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("");
        mockMvc.perform(post("/books/edit")
                        .flashAttr("book", bookRequestDto))
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("book", "title"));
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(post("/books/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }
}

