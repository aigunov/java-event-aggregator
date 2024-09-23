package ru.practicum.mainservice.data.model.exceptions;

public class EntityUpdateConflict extends RuntimeException {
    public EntityUpdateConflict(String message) {
        super(message);
    }
}
