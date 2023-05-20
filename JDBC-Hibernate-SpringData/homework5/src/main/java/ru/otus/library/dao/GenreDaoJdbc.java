package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public Genre save(Genre genre) {
        String sql = "INSERT INTO genre (genre_name) VALUES (:genreName)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("genreName", genre.getGenreName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        genre.setId(keyHolder.getKey().longValue());
        return genre;
    }

    @Override
    public boolean update(Genre genre) {
        String sql = "UPDATE genre SET genre_name = :genreName WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("genreName", genre.getGenreName());
        int rowsAffected = jdbcTemplate.update(sql, params);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Genre> getById(long id) {
        String sql = "SELECT id, genre_name FROM genre WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        List<Genre> genres = jdbcTemplate.query(sql, params, new GenreRowMapper());
        return genres.isEmpty() ? Optional.empty() : Optional.of(genres.get(0));
    }

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT id, genre_name FROM genre";
        return jdbcTemplate.query(sql, new GenreRowMapper());
    }

    @Override
    public boolean deleteById(long id) {
        String sql = "DELETE FROM genre WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        int rowsAffected = jdbcTemplate.update(sql, params);
        return rowsAffected > 0;
    }

    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String genreName = resultSet.getString("genre_name");
            return new Genre(id, genreName);
        }
    }
}
