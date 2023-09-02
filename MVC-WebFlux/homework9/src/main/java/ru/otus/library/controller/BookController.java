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
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookRequestDto;
import ru.otus.library.exeption.NotFoundException;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;
import ru.otus.library.util.MapperDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final MapperDto mapper;

    @GetMapping("books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("books/create")
    public String goToCreateBook(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("book", new BookRequestDto());
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
    return "book/create";
    }

    @PostMapping("books/create")
    public String createBook(@ModelAttribute("book") @Valid BookRequestDto book,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Author> authors = authorService.getAllAuthors();
            List<Genre> genres = genreService.getAllGenres();
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            return "book/create";
        }
        bookService.saveBook(book);
      return "redirect:/books";
    }

    @GetMapping("books/edit/{id}")
    public String goToEditBook(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        BookRequestDto bookRequestDto = mapper.convertFromBookToBookDto(book);
        List<Author> authors = authorService.getAllAuthors();
        List<Genre> genres = genreService.getAllGenres();
        model.addAttribute("book", bookRequestDto);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book/edit";
    }

    @PostMapping("books/edit")
    public String editBook(@ModelAttribute("book") @Valid BookRequestDto book,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Author> authors = authorService.getAllAuthors();
            List<Genre> genres = genreService.getAllGenres();
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            return "book/edit";
        }
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @PostMapping ("/books/{id}")
    public String deleteBook(@PathVariable("id") long id) {
    bookService.deleteBookById(id);
    return "redirect:/books";
    }
}
