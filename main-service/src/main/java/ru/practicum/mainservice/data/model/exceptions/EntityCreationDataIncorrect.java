package ru.practicum.mainservice.data.model.exceptions;

public class EntityCreationDataIncorrect extends RuntimeException {
    public EntityCreationDataIncorrect(String message) {
        super(message);
    }
}
