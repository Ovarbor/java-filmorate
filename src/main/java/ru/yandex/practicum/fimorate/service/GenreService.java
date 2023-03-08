package ru.yandex.practicum.fimorate.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.fimorate.exceptions.NotFoundValidationException;
import ru.yandex.practicum.fimorate.model.Genre;
import ru.yandex.practicum.fimorate.storage.GenreStorage;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;

    public Collection<Genre> findAll() {
        return genreStorage.findAll();
    }

    public Genre getById (Long id) {
        return genreStorage.getById(id).orElseThrow(() ->
                new NotFoundValidationException("Жанр с id " + id + " не найден"));
    }
}
