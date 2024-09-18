package ru.practicum.mainservice.data.model;

public class EntityCreationDataIncorrect extends RuntimeException {
    public EntityCreationDataIncorrect(String message) {
        super(message);
    }
}
