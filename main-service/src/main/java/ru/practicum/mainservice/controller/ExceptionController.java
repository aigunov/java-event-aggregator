package ru.practicum.mainservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.practicum.mainservice.data.model.exceptions.CommentActionException;
import ru.practicum.mainservice.data.model.exceptions.EntityCreationDataIncorrect;
import ru.practicum.mainservice.data.model.exceptions.EntityUpdateConflict;
import ru.practicum.mainservice.data.model.exceptions.NoValidParameter;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice("ru.practicum")
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<String>> handleNoValidRequestBodyException(final MethodArgumentNotValidException e) {
        List<String> descriptionViolations = e.getFieldErrors().stream()
                .map(x -> x.getField() + " -> " + x.getDefaultMessage())
                .collect(Collectors.toList());
        log.error("Получен статус 400 BAD_REQUEST. Тело запроса содержит невалидные данные: {}.", descriptionViolations);
        return Map.of("Тело запроса содержит некорректные данные", descriptionViolations);
    }

    @ExceptionHandler(NoValidParameter.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNoValidParameter(final NoValidParameter e) {
        log.debug("Получен статус 400 BAD_REQUEST {}", e.getMessage());
        return Map.of("Ошибка: ", e.getMessage());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleHandlerMethodValidationException(final MethodArgumentNotValidException e) {
        List<String> descriptionViolations = e.getFieldErrors().stream()
                .map(x -> x.getField() + " -> " + x.getDefaultMessage())
                .collect(Collectors.toList());
        log.error("Получен статус 400 BAD_REQUEST. Тело запроса содержит невалидные данные: {}.", descriptionViolations);
        return Map.of("Тело запроса содержит некорректные данные", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMissingURLParameter(MissingServletRequestParameterException e) {
        log.error("Получен статус 400 BAD_REQUEST {}", e.getMessage());
        return Map.of("Ошибка: ", e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundEntity(NoSuchElementException e) {
        log.error("Получен статус 404 NOT_FOUND {}", e.getMessage());
        return Map.of("Ошибка: ", e.getMessage());
    }

    @ExceptionHandler(EntityUpdateConflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflictEntity(final EntityUpdateConflict e) {
        log.error("Получен статус 409 CONFLICT {}", e.getMessage());
        return Map.of("Ошибка", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflict(final EntityCreationDataIncorrect e) {
        log.error("Получен статус 409 CONFLICT {}", e.getMessage());
        return Map.of("Ошибка", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleCommentException(final CommentActionException e) {
        log.error("Получен Статус 409 CONFLIC {}", e.getMessage());
        return Map.of("Ошибка", e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConstraintViolationException(final DataIntegrityViolationException e) {
        log.error("Получен статус 409 CONFLICT {}", e.getMessage());
        return Map.of("Ошибка", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleServerException(Exception e) {
        log.error("Получен статус 500 SERVER_ERROR {}", e.getStackTrace());
        return Map.of("Ошибка: ", e.getMessage());
    }

}
