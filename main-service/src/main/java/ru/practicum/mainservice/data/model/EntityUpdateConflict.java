package ru.practicum.mainservice.data.model;

public class EntityUpdateConflict extends RuntimeException {
    public EntityUpdateConflict(String message) {
        super(message);
    }
}
