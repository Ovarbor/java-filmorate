package ru.yandex.practicum.fimorate.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestValidationException extends RuntimeException {
    public BadRequestValidationException(final String message) {
        super(message);
    }
}
