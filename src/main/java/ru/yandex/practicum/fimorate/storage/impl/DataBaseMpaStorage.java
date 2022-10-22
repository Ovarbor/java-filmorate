package ru.yandex.practicum.fimorate.storage.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.fimorate.mappers.MpaRowMapper;
import ru.yandex.practicum.fimorate.model.Mpa;
import ru.yandex.practicum.fimorate.storage.MpaStorage;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class DataBaseMpaStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Mpa> findAll() {
        return jdbcTemplate.query("SELECT * FROM MPA ORDER BY ID", new MpaRowMapper());
    }

    @Override
    public Optional<Mpa> getById(Long id) {
        String sqlQuery = "SELECT * FROM MPA WHERE ID = ?";
        List<Mpa> mpa = jdbcTemplate.query(sqlQuery, new MpaRowMapper(), id);
        return mpa.stream().findFirst();
    }
}
