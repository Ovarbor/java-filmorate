package ru.yandex.practicum.fimorate.exceptions;

public class IllegalRequestException extends RuntimeException {
    public IllegalRequestException(String message) {
        super(message);
    }
}
