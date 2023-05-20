package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO book (title, publication_date, author_id, genre_id)" +
                " VALUES (:title, :publicationDate, :authorId, :genreId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("publicationDate", book.getPublicationDate())
                .addValue("authorId", book.getAuthor().getId())
                .addValue("genreId", book.getGenre().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        book.setId(keyHolder.getKey().longValue());
        return book;
    }

    @Override
    public boolean update(Book book) {
        String sql = "UPDATE book SET title = :title," +
                " publication_date = :publicationDate," +
                " author_id = :authorId, genre_id =:genreId WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle())
                .addValue("publicationDate", book.getPublicationDate())
                .addValue("authorId", book.getAuthor().getId())
                .addValue("genreId", book.getGenre().getId());
        int rowsAffected = jdbcTemplate.update(sql, params);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Book> getById(long id) {
        String sql = "SELECT b.id as id, b.title, b.publication_date, a.id as author_id, a.first_name," +
                " a.last_name, a.date_of_birth, g.id as genre_id, g.genre_name FROM book b INNER JOIN author a " +
                "ON b.author_id = a.id INNER JOIN genre g ON b.genre_id = g.id WHERE b.id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        List<Book> books = jdbcTemplate.query(sql, params, new BookRowMapper());
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public List<Book> getAll() {
        String sql = "SELECT b.id as id, b.title, b.publication_date, a.id as author_id, a.first_name," +
                "a.last_name, a.date_of_birth, g.id as genre_id, g.genre_name FROM book b INNER JOIN author a " +
                "ON b.author_id = a.id INNER JOIN genre g ON b.genre_id = g.id";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    @Override
    public boolean deleteById(long id) {
        String sql = "DELETE FROM book WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        int rowsAffected = jdbcTemplate.update(sql, params);
        return rowsAffected > 0;
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long bookId = resultSet.getLong("id");
            String title = resultSet.getString("title");
            LocalDate publicationDate = LocalDate.parse(resultSet.getString("publication_date"));
            long authorId = resultSet.getLong("author_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            LocalDate dateOfBirth = LocalDate.parse(resultSet.getString("date_of_birth"));
            Author author = new Author(authorId, firstName, lastName, dateOfBirth);
            long genreId = resultSet.getLong("genre_id");
            String genreName = resultSet.getString("genre_name");
            Genre genre = new Genre(genreId, genreName);
            return new Book(bookId, title, publicationDate, author, genre);
        }
    }
}
