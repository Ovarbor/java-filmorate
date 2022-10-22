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
        log.info("Данные пользователя с id " + user.getId() + " обновлены");
        return userStorage.put(user);
    }

    public User getById(Long id) {
        if (userStorage.findAll().contains(userStorage.getById(id))) {
            return userStorage.getById(id);
        } else {
            throw new NotFoundValidationException("Пользователь с id " + id + " не найден");
        }
    }

    public Collection<User> findAll() {
        log.info("Текущее количество пользователей: {}", userStorage.findAll().size());
        return userStorage.findAll();
    }

    public void addFriend(Long id, Long friendId) {
        Map<Long, User> users = validateUsersById(id, friendId);
        userStorage.addFriend(users.get(id), users.get(friendId));
        users.get(id).getFriends().add(friendId);
        users.get(friendId).getFriends().add(id);
        log.info("Друг добавлен");
    }

    public void deleteFriend(Long id, Long friendId) {
        Map<Long, User> users = validateUsersById(id, friendId);
        userStorage.deleteFriend(users.get(id), users.get(friendId));
        users.get(id).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(id);
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
        User user = userStorage
                .getById(id)
                .orElseThrow(() -> new NotFoundValidationException("Пользователь с id " + id + " не найден"));
        return user
                .getFriends()
                .stream()
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public Map<Long, User> validateUsersById(Long firstId, Long secondId) {
        if (firstId.equals(secondId)) {
            throw new IllegalRequestException("Id пользователей не должны совпадать");
        }
        User firstUser = userStorage
                .getById(firstId)
                .orElseThrow(() -> new NotFoundValidationException("Пользователь с id " + firstId + " не найден"));
        User secondUser = userStorage
                .getById(secondId)
                .orElseThrow(() -> new NotFoundValidationException("Пользователь с id " + secondId + " не найден"));
        if (userStorage.findAll().contains(userStorage.getById(id))) {
            User user = userStorage.getById(id);
            log.info("Друзья найдены");
            return user
                    .getFriends()
                    .stream()
                    .map(this::getById)
                    .collect(Collectors.toList());
        } else {
            throw new NotFoundValidationException("Пользователь с id " + id + " не найден");
        }
    }

    public Map<Long, User> validateUsersById(Long firstId, Long secondId) {
        User firstUser;
        User secondUser;
        if (firstId.equals(secondId)) {
            throw new IllegalRequestException("Id пользователей не должны совпадать");
        }
        if (userStorage.findAll().contains(userStorage.getById(firstId))) {
            firstUser = userStorage.getById(firstId);
        } else {
            throw new NotFoundValidationException("Пользователь с id " + firstId + " не найден");
        }
        if (userStorage.findAll().contains(userStorage.getById(secondId))) {
            secondUser = userStorage.getById(secondId);
        } else {
            throw new NotFoundValidationException("Пользователь с id " + secondId + " не найден");
        }
        Map<Long, User> users = new HashMap<>();
        users.put(firstId, firstUser);
        users.put(secondId, secondUser);
        return users;
    }

    private void createValidator(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    protected void updateValidator(User user) {
        Optional<User> userOptional = userStorage.getById(user.getId());
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (userOptional.isEmpty()) {
            throw new NotFoundValidationException("Пользователь с id " + user.getId() + " не найден");
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (!userStorage.findAll().contains(userStorage.getById(user.getId()))) {
            throw new NotFoundValidationException("Пользователь с таким id не найден");
        }
    }
}
