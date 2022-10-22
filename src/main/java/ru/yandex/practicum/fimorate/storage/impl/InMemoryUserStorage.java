package ru.yandex.practicum.fimorate.storage.impl;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.fimorate.model.User;
import ru.yandex.practicum.fimorate.storage.UserStorage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User put(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public Optional<User> getById(Long id) {
        return users.containsKey(id) ? Optional.of(users.get(id)) : Optional.empty();
    }

    @Override
    public void addFriend(User user, User friend) {
        users.get(user.getId()).getFriends().add(friend.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        users.get(user.getId()).getFriends().remove(friend.getId());
    }
}
