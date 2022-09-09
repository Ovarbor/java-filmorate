package ru.yandex.practicum.fimorate.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fimorate.exceptions.*;
import ru.yandex.practicum.fimorate.model.Film;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 0;

    @PostMapping
    public Film create(@RequestBody Film film) {
        film.setId(++filmId);
        LocalDate Date = LocalDate.of(1895, Month.DECEMBER, 28);
        if (film.getName() == null || film.getName().isBlank()) {
            throw new BadRequestValidationException("Название не может быть пустым.");
        }
        if (film.getDescription().length() >= 200) {
            throw new BadRequestValidationException("Максимальная длина описания — 200 символов.");
        }
        if (film.getReleaseDate().isBefore(Date)) {
            throw new BadRequestValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
        }
        if (film.getDuration() <= 0) {
            throw new BadRequestValidationException("Продолжительность фильма должна быть положительной.");
        }
        log.debug("Фильм сохранён: {}", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film put(@RequestBody Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new BadRequestValidationException("Название не может быть пустым.");
        }
        if (film.getId() < 0) {
            throw new NotFoundValidationException("Идентификатор не может быть отрицательным.");
        }
        log.debug("Фильм с идентификатором " + film.getId() + " изменён {}", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();    }
}
