package ru.yandex.practicum.fimorate.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.fimorate.exceptions.IllegalRequestException;
import ru.yandex.practicum.fimorate.exceptions.NotFoundValidationException;
import ru.yandex.practicum.fimorate.model.Film;
import ru.yandex.practicum.fimorate.model.Genre;
import ru.yandex.practicum.fimorate.model.Mpa;
import ru.yandex.practicum.fimorate.model.User;
import ru.yandex.practicum.fimorate.storage.FilmStorage;
import ru.yandex.practicum.fimorate.storage.GenreStorage;
import ru.yandex.practicum.fimorate.storage.MpaStorage;
import ru.yandex.practicum.fimorate.storage.UserStorage;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
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
        loadMpa(film);
        loadGenres(film);
        filmStorage.put(film);
        log.info("Фильм с id " + film.getId() + " обновлен");
        return film;
    }

    public Film getById(Long id) {
        Optional<Film> film = filmStorage.getById(id);
        return film.orElseThrow(() -> new NotFoundValidationException("Фильм с id " + id + " не найден"));
    }

    public Collection<Film> findAll() {
        log.info("Текущее количество фильмов: {}", filmStorage.findAll().size());
        return filmStorage.findAll();
    }

    public void addLike(Long id, Long userId) {
        Film film = filmStorage
                .getById(id)
                .orElseThrow(() -> new NotFoundValidationException("Фильм с id " + id + " не найден"));
        User user = userStorage
                .getById(userId)
                .orElseThrow(() -> new NotFoundValidationException("Пользователь с id " + userId + " не найден"));
        filmStorage.addLike(film, user);
        log.info("Лайк поставлен!");
    }

    public void deleteLike(Long id, Long userId) {
        Film film = filmStorage
                .getById(id)
                .orElseThrow(() -> new NotFoundValidationException("Фильм с id " + id + " не найден"));
        User user = userStorage
                .getById(userId)
                .orElseThrow(() -> new NotFoundValidationException("Пользователь с id " + userId + " не найден"));
        filmStorage.deleteLike(film, user);
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
        Optional<Film> filmOptional = filmStorage.getById(film.getId());
        if (filmOptional.isEmpty()) {
            throw new NotFoundValidationException("Фильм с id " + film.getId() + " не найден");
        }
    }

    private void loadMpa(Film film) {
        Optional<Mpa> mpaOptional = mpaStorage.getById(film.getMpa().getId());
        if (mpaOptional.isEmpty()) {
            throw new NotFoundValidationException("Рейтинг MPA с id " + film.getMpa().getId() + " не найден");
        }
        film.setMpa(mpaOptional.get());
    }

    private void loadGenres(Film film) {
        Set<Genre> genreSet = new TreeSet<>(film.getGenres());
        film.getGenres().clear();
        for (Genre genre: genreSet) {
            Optional<Genre> genreOptional = genreStorage.getById(genre.getId());
            if (genreOptional.isEmpty()) {
                throw new NotFoundValidationException("Жанр с id " + genre.getId() + " не найден");
            }
            film.getGenres().add(genreOptional.get());
        }
    }
}
