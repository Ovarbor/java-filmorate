package ru.yandex.practicum.fimorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.fimorate.model.User;
import ru.yandex.practicum.fimorate.service.UserService;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        User userCreated = userService.create(user);
        return ResponseEntity.status(201).body(userCreated);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) {
        User userUpdated = userService.update(user);
        return ResponseEntity.ok().body(userUpdated);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> findAll() {
        Collection<User> users  = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<Collection<User>> findFriendsById(@PathVariable Long id) {
        Collection<User> friends = userService.findFriendsById(id);
        return ResponseEntity.ok().body(friends);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> findCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        List<User> commonFriends = userService.findCommonFriends(id, otherId);
        return ResponseEntity.ok().body(commonFriends);
    }
}
