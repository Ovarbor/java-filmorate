package ru.yandex.practicum.fimorate.storage;
import ru.yandex.practicum.fimorate.model.User;
import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    User create(User user);

    User put(User user);

    Collection<User> findAll();

    Optional<User> getById(Long Id);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    User getById(Long Id);
}
