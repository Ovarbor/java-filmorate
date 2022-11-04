package ru.yandex.practicum.fimorate.mappers;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.fimorate.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));
        if (Objects.nonNull(rs.getDate("birthday"))) {
            user.setBirthday(rs.getDate("birthday").toLocalDate());
        }
        return user;
    }
}
