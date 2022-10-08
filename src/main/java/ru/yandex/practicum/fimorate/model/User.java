package ru.yandex.practicum.fimorate.model;
import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    @Email
    @NotBlank
    private String email;
    @NotBlank(message = "Логин не может быть пустым и содержать пробелы.")
    @Pattern(regexp = "^\\S*$", message = "Логин не может быть пустым и содержать пробелы.")
    private String login;
    private String name;
    @Past(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}

