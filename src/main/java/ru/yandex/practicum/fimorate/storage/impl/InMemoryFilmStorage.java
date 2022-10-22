package ru.yandex.practicum.fimorate.storage.impl;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.fimorate.model.Film;
import ru.yandex.practicum.fimorate.model.User;
import ru.yandex.practicum.fimorate.storage.FilmStorage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film create(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film put(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Optional<Film> getById(Long id) {
        return films.containsKey(id) ? Optional.of(films.get(id)) : Optional.empty();
    }

    @Override
    public void addLike(Film film, User user) {
        films.get(film.getId()).getLikes().add(user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        films.get(film.getId()).getLikes().remove(user.getId());
    }
}
