package ru.practicum.mainservice.data.model.exceptions;

public class EntityIsRelatedWithOthers extends RuntimeException {
    public EntityIsRelatedWithOthers(final String theCategoryIsNotEmpty) {
        super(theCategoryIsNotEmpty);
    }
}
