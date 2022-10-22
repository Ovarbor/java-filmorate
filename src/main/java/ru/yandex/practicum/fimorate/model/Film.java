package ru.yandex.practicum.fimorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;
    @NotBlank(message = "Название не может быть пустым.")
    private String name;
    @Length(max = 200)
    private String description;
    private LocalDate releaseDate;
    @Min(0)
    private long duration;
    private Integer rate;
    private final Set<Long> likes = new HashSet<>();
    @NotNull
    private Mpa mpa;
    private final Set<Genre> genres = new TreeSet<>();

    public Film(String name, String description, LocalDate releaseDate, long duration, int i, Mpa g) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
