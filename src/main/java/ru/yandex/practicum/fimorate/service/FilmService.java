package ru.yandex.practicum.fimorate.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.fimorate.exceptions.IllegalRequestException;
import ru.yandex.practicum.fimorate.exceptions.NotFoundValidationException;
import ru.yandex.practicum.fimorate.model.Film;
import ru.yandex.practicum.fimorate.model.User;
import ru.yandex.practicum.fimorate.storage.FilmStorage;
import ru.yandex.practicum.fimorate.storage.UserStorage;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private long filmId = 0;

    public Film create(Film film) {
        createValidator(film);
        film.setId(++filmId);
        log.info("Фильм с id " + film.getId() + " сохранён");
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        updateValidator(film);
        log.info("Фильм с id " + film.getId() + " изменён");
        return filmStorage.put(film);
    }

    public Film getById(Long id) {
        if (filmStorage.findAll().contains(filmStorage.getById(id))) {
            return filmStorage.getById(id);
        } else {
            throw new NotFoundValidationException("Фильм с id " + id + " не найден");
        }
    }

    public Collection<Film> findAll() {
        log.info("Текущее количество фильмов: {}", filmStorage.findAll().size());
        return filmStorage.findAll();
    }

    public void addLike(Long id, Long userId) {
        Film film = filmStorage.getById(id);
        User user = userStorage.getById(userId);
        film.getLikes().add(user.getId());
        log.info("Лайк поставлен!");
    }

    public void deleteLike(Long id, Long userId) {
        User user;
        Film film = filmStorage.getById(id);
        if (userStorage.findAll().contains(userStorage.getById(userId))) {
            user = userStorage.getById(userId);
        } else {
            throw new NotFoundValidationException("Пользователь с id " + userId + " не найден");
        }
        film.getLikes().remove(user.getId());
        log.info("Лайк удалён!");
    }

    public List<Film> findPopular(Integer count) {
        List<Film> popularFilm =  filmStorage
                .findAll()
                .stream()
                .sorted(Comparator.comparing(film -> film.getLikes().size()))
                .collect(Collectors.toList());
        Collections.reverse(popularFilm);
        return popularFilm
                .stream()
                .limit(count)
                .collect(Collectors.toList());
    }

    private void createValidator(Film film) {
        LocalDate Date = LocalDate.of(1895, Month.DECEMBER, 28);
        if (film.getReleaseDate().isBefore(Date)) {
            throw new IllegalRequestException("Дата релиза — не раньше 28 декабря 1895 года.");
        }
    }

    private void updateValidator(Film film) {
        LocalDate Date = LocalDate.of(1895, Month.DECEMBER, 28);
        if (film.getReleaseDate().isBefore(Date)) {
            throw new IllegalRequestException("Дата релиза — не раньше 28 декабря 1895 года.");
        }
        if (filmStorage.findAll().contains(filmStorage.getById(film.getId()))) {
            filmStorage.getById(film.getId());
        } else {
            throw new NotFoundValidationException("Пользователь с id не найден");
        }
    }
}
