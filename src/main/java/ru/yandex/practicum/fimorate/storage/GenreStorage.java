package ru.yandex.practicum.fimorate.storage;
import ru.yandex.practicum.fimorate.model.Genre;
import java.util.Collection;
import java.util.Optional;

public interface GenreStorage {

    Collection<Genre> findAll();

    Optional <Genre> getById(Long id);
}
