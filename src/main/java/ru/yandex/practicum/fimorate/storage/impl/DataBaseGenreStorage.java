package ru.yandex.practicum.fimorate.storage.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.fimorate.mappers.GenreRowMapper;
import ru.yandex.practicum.fimorate.model.Genre;
import ru.yandex.practicum.fimorate.storage.GenreStorage;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class DataBaseGenreStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Genre> findAll() {
        return jdbcTemplate.query("SELECT * FROM genre ORDER BY ID", new GenreRowMapper());
    }

    @Override
    public Optional<Genre> getById(Long id) {
        String sqlQuery = "SELECT * FROM genre WHERE ID = ?";
        List<Genre> genres = jdbcTemplate.query(sqlQuery, new GenreRowMapper(), id);
        return genres.stream().findFirst();
    }
}


