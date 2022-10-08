package ru.yandex.practicum.fimorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fimorate.exceptions.NotFoundValidationException;
import ru.yandex.practicum.fimorate.model.User;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 0;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        user.setId(++userId);
        validator(user);
        log.debug("Пользователь сохранён: {}", user.getName());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        validator(user);
        log.debug("Пользователь с идентификатором " + user.getId() + " изменён {}", user.getName());
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public Collection<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    private void validator(User user) {
        if (user.getId() < 0) {
            throw new NotFoundValidationException("Идентификатор не может быть отрицательным.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
