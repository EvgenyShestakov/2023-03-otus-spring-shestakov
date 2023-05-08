package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public Author save(Author author) {
        String sql = "INSERT INTO author (last_name, first_name, date_of_birth)" +
                " VALUES (:lastName, :firstName, :dateOfBirth)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("lastName", author.getLastName())
                .addValue("firstName", author.getFirstName())
                .addValue("dateOfBirth", author.getDateOfBirth());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        author.setId(keyHolder.getKey().longValue());
        return author;
    }

    @Override
    public boolean update(Author author) {
        String sql = "UPDATE author SET first_name = :firstName," +
                " last_name = :lastName, date_of_birth = :dateOfBirth WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("firstName", author.getFirstName())
                .addValue("lastName", author.getLastName())
                .addValue("dateOfBirth", author.getDateOfBirth());
        int rowsAffected = jdbcTemplate.update(sql, params);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Author> getById(long id) {
        String sql = "SELECT id, last_name, first_name, date_of_birth FROM author WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        List<Author> authors = jdbcTemplate.query(sql, params, new AuthorRowMapper());
        return authors.isEmpty() ? Optional.empty() : Optional.of(authors.get(0));
    }

    @Override
    public List<Author> getAll() {
        String sql = "SELECT id, last_name, first_name, date_of_birth FROM author";
        return jdbcTemplate.query(sql, new AuthorRowMapper());
    }

    @Override
    public boolean deleteById(long id) {
        String sql = "DELETE FROM author WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        int rowsAffected = jdbcTemplate.update(sql, params);
        return rowsAffected > 0;
    }

    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            LocalDate dateOfBirth = LocalDate.parse(resultSet.getString("date_of_birth"));
            return new Author(id, firstName, lastName, dateOfBirth);
        }
    }
}