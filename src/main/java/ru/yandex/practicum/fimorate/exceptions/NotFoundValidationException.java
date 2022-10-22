package ru.yandex.practicum.fimorate.exceptions;

public class NotFoundValidationException extends RuntimeException {
    public NotFoundValidationException(String message) {
        super(message);
    }
}
