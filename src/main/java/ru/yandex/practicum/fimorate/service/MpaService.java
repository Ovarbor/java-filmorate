package ru.yandex.practicum.fimorate.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.fimorate.exceptions.NotFoundValidationException;
import ru.yandex.practicum.fimorate.model.Mpa;
import ru.yandex.practicum.fimorate.storage.MpaStorage;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaStorage mpaStorage;

    public Collection<Mpa> findAll() {
        return mpaStorage.findAll();
    }

    public Mpa getById(Long id) {
        return mpaStorage.getById(id).orElseThrow(() ->
                new NotFoundValidationException("Рейтинг с id " + id + " не найден"));
    }

}
