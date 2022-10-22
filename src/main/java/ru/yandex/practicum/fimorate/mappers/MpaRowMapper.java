package ru.yandex.practicum.fimorate.mappers;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.fimorate.model.Mpa;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MpaRowMapper implements RowMapper<Mpa> {
    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getLong("id"), rs.getString("name"));
    }
}
