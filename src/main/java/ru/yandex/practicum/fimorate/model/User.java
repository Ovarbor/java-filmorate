package ru.yandex.practicum.fimorate.model;
import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    @Email
    @NotBlank
    private String email;
    @NotBlank(message = "Логин не может быть пустым и содержать пробелы.")
    @Pattern(regexp = "^\\S*$", message = "Логин не может быть пустым и содержать пробелы.")
    private String login;
    private String name;
    @Past(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;
    private final Set<Long> friends = new HashSet<>();

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}

