package ru.practicum.mainservice.data.model.exceptions;

public class NoValidParameter extends RuntimeException {
    public NoValidParameter(String mes) {
        super(mes);
    }
}
