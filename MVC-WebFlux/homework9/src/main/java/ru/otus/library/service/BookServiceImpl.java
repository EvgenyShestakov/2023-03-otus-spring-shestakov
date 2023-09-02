package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookRequestDto;
import ru.otus.library.exeption.NotFoundException;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public Book saveBook(BookRequestDto bookRequestDto) throws NoSuchElementException {
        Author author = authorRepository.findById(bookRequestDto.getAuthorId()).
                orElseThrow(() -> new NoSuchElementException("Author not found"));
        Genre genre = genreRepository.findById(bookRequestDto.getGenreId()).
                orElseThrow(() -> new NoSuchElementException("Genre not found"));
        Book book = new Book(bookRequestDto.getTitle(), author, genre);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void updateBook(BookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(bookRequestDto.getBookId()).
                orElseThrow(() -> new NotFoundException("Book not found"));
        Author author = authorRepository.findById(bookRequestDto.
                getAuthorId()).orElseThrow(() -> new NotFoundException("Author not found"));
        Genre genre = genreRepository.findById(bookRequestDto.getGenreId()).
                orElseThrow(() -> new NotFoundException("Genre not found"));
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(author);
        book.setGenre(genre);
        bookRepository.saveAndFlush(book);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
        bookRepository.delete(book);
    }
}
