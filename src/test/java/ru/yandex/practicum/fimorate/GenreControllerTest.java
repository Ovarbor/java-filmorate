package ru.yandex.practicum.fimorate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.fimorate.model.Genre;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerTest {

    private List<Genre> genres;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void beforeEach() {
        genres = new ArrayList<>();
        genres.add(new Genre(1L, "Комедия"));
        genres.add(new Genre(2L, "Драма"));
        genres.add(new Genre(3L, "Мультфильм"));
        genres.add(new Genre(4L, "Триллер"));
        genres.add(new Genre(5L, "Документальный"));
        genres.add(new Genre(6L, "Боевик"));
    }

    @DirtiesContext
    @Test
    public void testGenresGetGenres() throws Exception {
        mockMvc.perform(
                        get(URI.create("/genres")))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres)));
        mockMvc.perform(
                        get(URI.create("/genres/6")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Боевик"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres.get(5))));
        mockMvc.perform(
                        get(URI.create("/genres/4")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Триллер"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres.get(3))));
    }

    @DirtiesContext
    @Test
    public void testGenreGetById() throws Exception {
        mockMvc.perform(
                        get(URI.create("/genres/2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Драма"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres.get(1))));
        mockMvc.perform(
                        get(URI.create("/genres/5")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Документальный"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres.get(4))));

    }

    @DirtiesContext
    @Test
    public void testGenreWithFailId() throws Exception {
        mockMvc.perform(
                        get(URI.create("/genres/-1")))
                .andExpect(status().isNotFound());
    }
}
