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
import ru.yandex.practicum.fimorate.model.Mpa;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MpaControllerTest {

    private List<Mpa> mpaList;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void beforeEach() {
        mpaList = new ArrayList<>();
        mpaList.add(new Mpa(1L, "G"));
        mpaList.add(new Mpa(2L, "PG"));
        mpaList.add(new Mpa(3L, "PG-13"));
        mpaList.add(new Mpa(4L, "R"));
        mpaList.add(new Mpa(5L, "NC-17"));
    }

    @DirtiesContext
    @Test
    public void testMpaGetMpaList() throws Exception {
        mockMvc.perform(
                        get(URI.create("/mpa")))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(mpaList)));
        mockMvc.perform(
                        get(URI.create("/mpa/5")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("NC-17"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(mpaList.get(4))));
        mockMvc.perform(
                        get(URI.create("/mpa/2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("PG"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(mpaList.get(1))));
    }

    @DirtiesContext
    @Test
    public void testMpaGetById() throws Exception {
        mockMvc.perform(
                        get(URI.create("/mpa/1")))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(mpaList.get(0))));
        mockMvc.perform(
                        get(URI.create("/mpa/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("G"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(mpaList.get(0))));
        mockMvc.perform(
                        get(URI.create("/mpa/4")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("R"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(mpaList.get(3))));
    }

    @DirtiesContext
    @Test
    public void testMpaWithFailId() throws Exception {
        mockMvc.perform(
                        get(URI.create("/mpa/-1")))
                .andExpect(status().isNotFound());
    }
}
