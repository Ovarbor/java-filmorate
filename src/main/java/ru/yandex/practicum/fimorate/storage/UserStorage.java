package ru.yandex.practicum.fimorate.storage;
import ru.yandex.practicum.fimorate.model.User;
import java.util.Collection;

public interface UserStorage {

    User create(User user);

    User put(User user);

    Collection<User> findAll();

    User getById(Long Id);
}
