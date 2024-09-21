package ru.practicum.mainservice.data.model.exceptions;

public class CommentActionException extends RuntimeException {
    public CommentActionException(String message) {
        super(message);
    }
}
