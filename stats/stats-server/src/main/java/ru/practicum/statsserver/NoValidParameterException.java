package ru.practicum.statsserver;

public class NoValidParameterException extends RuntimeException {
    public NoValidParameterException(String message) {
        super(message);
    }
}
