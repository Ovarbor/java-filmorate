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
import ru.yandex.practicum.fimorate.exceptions.IllegalRequestException;
import ru.yandex.practicum.fimorate.exceptions.NotFoundValidationException;
import ru.yandex.practicum.fimorate.model.Film;
import ru.yandex.practicum.fimorate.model.Mpa;
import ru.yandex.practicum.fimorate.model.User;
import ru.yandex.practicum.fimorate.storage.FilmStorage;
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
public class FilmControllerTest {

    private Film film1;
    private Film film2;
    private Film film3;
    private User user1;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FilmStorage filmStorage;

    @Autowired
    private UserStorage userStorage;

    @BeforeEach
    void beforeEach() {
        film1 = new Film(1L,"film1", "film1", LocalDate.of(2001, Month.AUGUST, 13), 60, 2, new Mpa(1L, "G"));
        film2 = new Film(2L,"film2", "film2", LocalDate.of(1992, Month.NOVEMBER, 12), 120, 2, new Mpa(1L, "G"));
        film3 = new Film(3L,"film3", "film3", LocalDate.of(1960, Month.JANUARY, 27), 30, 2, new Mpa(1L, "G"));
        user1 = new User("1111@gmail.com", "newlogin", "newname", LocalDate.of(2001, Month.AUGUST, 13));
    }

    @DirtiesContext
    @Test
    public void createFilm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(mapper.writeValueAsString(film1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("film1"));
    }

    @DirtiesContext
    @Test
    public void updateFilm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(mapper.writeValueAsString(film1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("film1"));
        film1 = new Film(1L, "FilmAboutMouse", "VeryGoodFilm", LocalDate.of(2001, Month.AUGUST, 15), 50, 2, new Mpa(1L, "G"));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/films")
                        .content(mapper.writeValueAsString(film1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("FilmAboutMouse"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("VeryGoodFilm"));
    }

    @DirtiesContext
    @Test
    public void getAllFilms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(mapper.writeValueAsString(film1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("film1"));
        film1 = new Film(1L, "FilmAboutMouse", "VeryGoodFilm", LocalDate.of(2001, Month.AUGUST, 15), 50, 2, new Mpa(1L, "G"));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/films")
                        .content(mapper.writeValueAsString(film1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("FilmAboutMouse"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("VeryGoodFilm"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(mapper.writeValueAsString(film2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("film2"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(mapper.writeValueAsString(film3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("film3"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/films")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @DirtiesContext
    @Test
    public void createFilmWithEmptyName() throws Exception {
        Film film = new Film(1L,"", "film1", LocalDate.of(2001, Month.AUGUST, 13), 180, 2, new Mpa(1L, "G"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DirtiesContext
    @Test
    public void createFilmWithLongDescription() throws Exception {
        Film film = new Film(1L, "film1", "film1sdfasfsadfasdfasfasfasdfasfczcvzxvvxzcvfweqfwe" +
                "frwerfewrfsdfsdfsafsdasdfasdfsadfsadfsadfasdfasdfasdfsadfsafsadfasdasdfsadfasdfadfasdfasd" +
                "fasdfasffdasdfasdfasdfasdfsadfasfffffffffffffffffffffffffffff",
                LocalDate.of(2001, Month.AUGUST, 13), 180, 2, new Mpa(1L, "G"));
        int count = 0;
        for (int i = 0; i < film.getDescription().length(); i++) {
            count++;
        }
        System.out.println("Количество символов в описании" + " " + count);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DirtiesContext
    @Test
    public void createFilmWithReleaseDateBefore28December1985() throws Exception {
        Film film = new Film(1L, "film1", "film1",
                LocalDate.of(1700, Month.AUGUST, 27), 210, 2, new Mpa(2L, "PG"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof IllegalRequestException))
                .andExpect(result -> assertEquals("Дата релиза — не раньше 28 декабря 1895 года.",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @DirtiesContext
    @Test
    public void createFilmWithNegativeDuration() throws Exception {
        Film film = new Film(1L, "film1", "film1",
                LocalDate.of(1999, Month.MAY, 11), -210, 2, new Mpa(1L, "G"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DirtiesContext
    @Test
    public void updateFilmWithEmptyName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(mapper.writeValueAsString(film1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("film1"));
        film1 = new Film(1L, "", "VertGoodFilm", LocalDate.of(2001, Month.AUGUST, 15), 50, 4, new Mpa(3L, "PG-13"));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/films")
                        .content(mapper.writeValueAsString(film1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DirtiesContext
    @Test
    public void updateFilmWithNegativeId() throws Exception {
        Film film = new Film("film1", "film1",
                LocalDate.of(1999, Month.MAY, 11), 210, 2, new Mpa(1L, "G"));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/films")
                .content(mapper.writeValueAsString(film))
                .contentType(MediaType.APPLICATION_JSON));
        film = new Film((long) -1, "123456", "123456", LocalDate.of(2001, Month.AUGUST, 18), 180, 4, new Mpa(3L, "PG-13"));
        Film finalFilm = film;
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/films")
                        .content(mapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundValidationException))
                .andExpect(result -> assertEquals("Фильм с id " + finalFilm.getId() + " не найден",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @DirtiesContext
    @Test
    public void testFilmWithFailId() throws Exception {
        mockMvc.perform(
                        get(URI.create("/films/-1")))
                .andExpect(status().isNotFound());
    }

    @DirtiesContext
    @Test
    public void testFilmWithLikeAndPopularWithoutCount() throws Exception {
        Film film = filmStorage.create(film1);
        User user = userStorage.create(user1);
        filmStorage.addLike(film, user);
        Film filmFromDB = filmStorage.getById(film.getId()).orElseThrow();
        mockMvc.perform(
                        get(URI.create("/films/popular")))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(filmFromDB))));
    }

    @DirtiesContext
    @Test
    public void testFilmWithLikeAndPopularWithCount() throws Exception {
        Film film = filmStorage.create(film1);
        User user = userStorage.create(user1);
        filmStorage.addLike(film, user);
        Film filmFromDB = filmStorage.getById(film.getId()).orElseThrow();
        mockMvc.perform(
                        get(URI.create("/films/popular?count=1")))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(filmFromDB))));
    }

    @DirtiesContext
    @Test
    public void testFilmAndUserDeleteLikeWith204() throws Exception {
        Film film = filmStorage.create(film1);
        User user = userStorage.create(user1);
        filmStorage.addLike(film, user);
        mockMvc.perform(
                        delete(URI.create("/films/" + film.getId() + "/like/" + user.getId())))
                .andExpect(status().isNoContent());
        Film filmFromDB = filmStorage.getById(film.getId()).orElseThrow();
        assertFalse(filmFromDB.getLikes().contains(user.getId()));
    }

    @DirtiesContext
    @Test
    public void givenFilmAndUserAddLikeWith204() throws Exception {
        Film film = filmStorage.create(film1);
        User user = userStorage.create(user1);
        mockMvc.perform(
                        put(URI.create("/films/" + film.getId() + "/like/" + user.getId())))
                .andExpect(status().isNoContent());
        Film filmFromDB = filmStorage.getById(film.getId()).orElseThrow();
        assertTrue(filmFromDB.getLikes().contains(user.getId()));
    }

    @DirtiesContext
    @Test
    public void givenFilmWithFailId404() throws Exception {
        mockMvc.perform(
                        get(URI.create("/films/-1")))
                .andExpect(status().isNotFound());
    }
}

