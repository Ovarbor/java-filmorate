package ru.yandex.practicum.fimorate.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fimorate.model.Film;
import ru.yandex.practicum.fimorate.service.FilmService;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        Film filmCreated = filmService.create(film);
        return ResponseEntity.status(201).body(filmCreated);
    }

    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {
        Film filmUpdated = filmService.update(film);
        return ResponseEntity.ok().body(filmUpdated);
    }

    @GetMapping
    public ResponseEntity<Collection<Film>> findAll() {
        Collection<Film> films  = filmService.findAll();
        return ResponseEntity.ok().body(films);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(@PathVariable Long id) {
        Film film = filmService.getById(id);
        return ResponseEntity.ok().body(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> findPopular(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return ResponseEntity.ok().body(filmService.findPopular(count));
    }
}
