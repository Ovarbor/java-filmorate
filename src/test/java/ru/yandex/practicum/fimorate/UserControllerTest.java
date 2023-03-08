package ru.yandex.practicum.fimorate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.fimorate.exceptions.NotFoundValidationException;
import ru.yandex.practicum.fimorate.model.User;
import ru.yandex.practicum.fimorate.storage.UserStorage;
import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private User user;
    private User user1;
    private User user2;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserStorage userStorage;

    @BeforeEach
    void beforeEach() {
        user = new User("1111@gmail.com", "newlogin", "newname", LocalDate.of(2001, Month.AUGUST, 13));
        user1 = new User("2222@mail.ru", "loginone", "nameone", LocalDate.of(2004, Month.DECEMBER, 17));
        user2 = new User("3333@yahoomail.com", "logintwo", "nametwo", LocalDate.of(1991, Month.APRIL, 22));
    }

    @DirtiesContext
    @Test
    public void createUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("newname"));
    }

    @DirtiesContext
    @Test
    public void updateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("newname"));
        user = new User(1L,"2@gmail.com", "newlogin2", "newname2", LocalDate.of(2001, Month.AUGUST, 15));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("newname2"));
    }

    @DirtiesContext
    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("newname"));
        user = new User(1L,"2@gmail.com", "newlogin2", "newname2", LocalDate.of(2001, Month.AUGUST, 15));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("newname2"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("nameone"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("nametwo"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @DirtiesContext
    @Test
    public void createUserWithIncorrectEmail() throws Exception {
        User user = new User("1111gmail.com", "newlogin", "newname", LocalDate.of(2001, Month.AUGUST, 13));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @DirtiesContext
    @Test
    public void createUserWithEmptyEmail() throws Exception {
        User user = new User("", "newlogin", "newname", LocalDate.of(2001, Month.AUGUST, 13));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @DirtiesContext
    @Test
    public void createUserWithEmptyLogin() throws Exception {
        User user = new User("1111@gmail.com", "", "newname", LocalDate.of(2001, Month.AUGUST, 13));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @DirtiesContext
    @Test
    public void createUserWithSpaceLogin() throws Exception {
        User user = new User("1111@gmail.com", "new login", "newname", LocalDate.of(2001, Month.AUGUST, 13));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @DirtiesContext
    @Test
    public void createUserWithEmptyName() throws Exception {
        User user = new User("1111@gmail.com", "newlogin", "", LocalDate.of(2001, Month.AUGUST, 13));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("newlogin"));
    }

    @DirtiesContext
    @Test
    public void createUserWithAfterBirthday() throws Exception {
        User user = new User("1111@gmail.com", "newlogin", "newname", LocalDate.of(2022, Month.NOVEMBER, 25));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @DirtiesContext
    @Test
    public void updateUserWithNegativeId() throws Exception {
        User user = new User("1111@gmail.com", "newlogin", "newname", LocalDate.of(2022, Month.AUGUST, 25));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON));
        user = new User((long) -1,"2@gmail.com", "newlogin2", "newname2", LocalDate.of(2001, Month.AUGUST, 15));
        User finalUser = user;
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundValidationException))
                .andExpect(result -> assertEquals("Пользователь с id " + finalUser.getId() + " не найден",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @DirtiesContext
    @Test
    public void testCommonFriendsGetCommonFriends() throws Exception {
        user = userStorage.create(user);
        User friend = userStorage.create(user1);
        User commonFriend = userStorage.create(user2);
        userStorage.addFriend(user, commonFriend);
        userStorage.addFriend(friend, commonFriend);
        mockMvc.perform(
                        get(URI.create("/users/" + user.getId() + "/friends/common/" + friend.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(commonFriend))));
    }

    @DirtiesContext
    @Test
    public void testUserAndFriendGetFriends() throws Exception {
        user = userStorage.create(user);
        User friend = userStorage.create(user1);
        userStorage.addFriend(user,friend);
        mockMvc.perform(
                        get(URI.create("/users/" + user.getId() + "/friends")))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(friend))));
    }

    @DirtiesContext
    @Test
    public void testUserAndFriendDeleteFriend204() throws Exception {
        User user = userStorage.create(user1);
        User friend = userStorage.create(user2);
        userStorage.addFriend(user,friend);
        mockMvc.perform(
                        delete(URI.create("/users/" + user.getId() + "/friends/" + friend.getId())))
                .andExpect(status().isNoContent());
        User userFromDB = userStorage.getById(user.getId()).orElseThrow();
        assertFalse(userFromDB.getFriends().contains(friend.getId()));
    }

    @DirtiesContext
    @Test
    public void testUserAndFriendWithFailIdAddFriend() throws Exception {
        mockMvc.perform(
                        put(URI.create("/users/1/friends/-1")))
                .andExpect(status().isNotFound());
    }

    @DirtiesContext
    @Test
    public void testUserAndFriendAddFriend204() throws Exception {
        User user = userStorage.create(user1);
        User friend = userStorage.create(user2);
        mockMvc.perform(
                        put(URI.create("/users/" + user.getId() + "/friends/" + friend.getId())))
                .andExpect(status().isNoContent());
        User userFromDB = userStorage.getById(user.getId()).orElseThrow();
        assertTrue(userFromDB.getFriends().contains(friend.getId()));
    }

    @Test
    public void testUserWithFailId404() throws Exception {
        mockMvc.perform(
                        get(URI.create("/users/-1")))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Пользователь с id " + -1 + " не найден",
                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}

