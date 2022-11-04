package ru.yandex.practicum.fimorate.mappers;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.fimorate.model.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getLong("genre.id"), rs.getString("genre.name"));
    }
}
