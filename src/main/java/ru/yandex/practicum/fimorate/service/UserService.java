package ru.yandex.practicum.fimorate.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.fimorate.exceptions.IllegalRequestException;
import ru.yandex.practicum.fimorate.exceptions.NotFoundValidationException;
import ru.yandex.practicum.fimorate.model.User;
import ru.yandex.practicum.fimorate.storage.UserStorage;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private long userId = 0;

    public User create(User user) {
        createValidator(user);
        user.setId(++userId);
        log.info("Пользователь с id " + user.getId() + " зарегистрирован");
        return userStorage.create(user);
    }

    public User update(User user) {
        updateValidator(user);
        userStorage.put(user);
        log.info("Данные пользователя с id " + user.getId() + " обновлены");
        return user;
    }

    public User getById(Long id) {
        Optional<User> user = userStorage.getById(id);
        return user.orElseThrow(() -> new NotFoundValidationException("Пользователь с id " + id + " не найден"));
    }

    public Collection<User> findAll() {
        log.info("Текущее количество пользователей: {}", userStorage.findAll().size());
        return userStorage.findAll();
    }

    public void addFriend(Long id, Long friendId) {
        Map<Long, User> users = validateUsersById(id, friendId);
        userStorage.addFriend(users.get(id), users.get(friendId));
        log.info("Друг добавлен");
    }

    public void deleteFriend(Long id, Long friendId) {
        Map<Long, User> users = validateUsersById(id, friendId);
        userStorage.deleteFriend(users.get(id), users.get(friendId));
        log.info("Друг удалён");
    }

    public List<User> findCommonFriends(Long id, Long otherId) {
        Map<Long, User> users = validateUsersById(id, otherId);
        Set<Long> userFriends = users.get(id).getFriends();
        Set<Long> otherFriends = users.get(otherId).getFriends();
        log.info("Друзья найдены");
        return userFriends
                .stream()
                .filter(otherFriends::contains)
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public List<User> findFriendsById(Long id) {
        User user = getUserById(id);
        log.info("Друг найден");
        return user
                .getFriends()
                .stream()
                .map(this::getById)
                .collect(Collectors.toList());
    }

    private Map<Long, User> validateUsersById(Long firstId, Long secondId) {
        if (firstId.equals(secondId)) {
            throw new IllegalRequestException("Id пользователей не должны совпадать");
        }
        Map<Long, User> users = new HashMap<>();
        users.put(firstId, getUserById(firstId));
        users.put(secondId, getUserById(secondId));
        return users;
    }

    private void createValidator(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private void updateValidator(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage
                .getById(user.getId())
                .orElseThrow(() -> new NotFoundValidationException("Пользователь с id " + user.getId() + " не найден"));
    }

    protected User getUserById(Long id) {
        return userStorage
                .getById(id)
                .orElseThrow(() -> new NotFoundValidationException("Пользователь с id " + id + " не найден"));
    }
}
