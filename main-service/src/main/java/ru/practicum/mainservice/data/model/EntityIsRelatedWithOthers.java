package ru.practicum.mainservice.data.model;

public class EntityIsRelatedWithOthers extends RuntimeException {
    public EntityIsRelatedWithOthers(final String theCategoryIsNotEmpty) {
        super(theCategoryIsNotEmpty);
    }
}
