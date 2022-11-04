package ru.yandex.practicum.fimorate.storage;
import ru.yandex.practicum.fimorate.model.Film;
import ru.yandex.practicum.fimorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    Film create(Film film);

    Film put(Film film);

    Collection<Film> findAll();

    Optional<Film> getById(Long Id);

    void addLike(Film film, User user);

    void deleteLike(Film film, User user);
}
