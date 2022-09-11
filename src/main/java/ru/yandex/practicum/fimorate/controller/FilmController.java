package ru.yandex.practicum.fimorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fimorate.exceptions.*;
import ru.yandex.practicum.fimorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 0;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        film.setId(++filmId);
        validator(film);
        log.debug("Фильм сохранён: {}", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        validator(film);
        log.debug("Фильм с идентификатором " + film.getId() + " изменён {}", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    private void validator(Film film) {
        LocalDate Date = LocalDate.of(1895, Month.DECEMBER, 28);
        if (film.getId() < 0) {
            throw new NotFoundValidationException("Идентификатор не может быть отрицательным.");
        }
        if (film.getReleaseDate().isBefore(Date)) {
            throw new BadRequestValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
        }
    }
}
