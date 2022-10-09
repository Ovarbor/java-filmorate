package ru.yandex.practicum.fimorate.storage;

import ru.yandex.practicum.fimorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film create(Film film);

    Film put(Film film);

    Collection<Film> findAll();

    Film getById(Long Id);
}
