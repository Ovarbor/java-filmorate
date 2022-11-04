package ru.yandex.practicum.fimorate.storage;
import ru.yandex.practicum.fimorate.model.Mpa;
import java.util.Collection;
import java.util.Optional;

public interface MpaStorage {

    Collection<Mpa> findAll();

    Optional <Mpa> getById(Long id);
}
