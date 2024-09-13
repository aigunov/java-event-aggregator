package ru.practicum.mainservice.data.model;

public class NoValidParameter extends RuntimeException {
    public NoValidParameter(String mes) {
        super(mes);
    }
}
