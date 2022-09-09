package ru.yandex.practicum.fimorate.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundValidationException extends RuntimeException {
    public NotFoundValidationException(final String message) {
        super(message);
    }
}
