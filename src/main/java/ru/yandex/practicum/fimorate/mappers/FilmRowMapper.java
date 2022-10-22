package ru.yandex.practicum.fimorate.mappers;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.fimorate.model.Film;
import ru.yandex.practicum.fimorate.model.Mpa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        if (Objects.nonNull(rs.getDate("release_date"))) {
            film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        }
        film.setDuration(rs.getLong("duration"));
        film.setRate(rs.getInt("rate"));
        film.setMpa(new Mpa(rs.getLong("mpa.id"), rs.getString("mpa.name")));
        return film;
    }
}
