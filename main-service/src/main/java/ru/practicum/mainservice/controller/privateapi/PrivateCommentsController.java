package ru.practicum.mainservice.controller.privateapi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.data.dto.comment.CommentDto;
import ru.practicum.mainservice.service.interfaces.CommentService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/event/{eventId}/comments")
@RequiredArgsConstructor
public class PrivateCommentsController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@PositiveOrZero @PathVariable Long userId,
                                    @PositiveOrZero @PathVariable Long eventId,
                                    @Valid @RequestBody CommentDto comment) {
        CommentDto createdComment = commentService.createComment(userId, eventId, comment);
        log.info("POST /users/{userId}/event/{eventId}/comments userId = {} eventId = {} createdComment = {}", userId,
                eventId, createdComment);
        return createdComment;
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PositiveOrZero @PathVariable Long userId,
                              @PositiveOrZero @PathVariable Long eventId,
                              @PositiveOrZero @PathVariable Long commentId) {
        log.info("DELETE /users/{userId}/event/{eventId}/comments/{commentId} userId = {}," +
                " eventId = {}, commentId = {}", userId, eventId, commentId);
        commentService.deleteComment(userId, eventId, commentId);
    }

    @GetMapping
    public List<CommentDto> getCommentsByEventId(@PositiveOrZero @PathVariable Long userId,
                                                 @PositiveOrZero @PathVariable Long eventId,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer size) {
        List<CommentDto> commentsByEventId = commentService.getCommentsByEventId(userId, eventId, PageRequest.of(from / size, size));
        log.info("GET /users/{userId}/event/{eventId}/comments userId = {} eventId = {} result = {}", userId,
                eventId, commentsByEventId);
        return commentsByEventId;
    }

    @GetMapping("/{commentId}")
    public CommentDto findComment(@PositiveOrZero @PathVariable Long userId,
                                  @PositiveOrZero @PathVariable Long eventId,
                                  @PositiveOrZero @PathVariable Long commentId) {
        log.info("GET /users/{userId}/event/{eventId}/comments/{commentId}" +
                " userId = {} eventId = {} commentId = {}", userId, eventId, commentId);
        CommentDto commentByIdAndEventId = commentService.getCommentByIdAndEventId(userId, eventId, commentId);
        log.info("Найденный комментарий {}", commentByIdAndEventId);
        return commentByIdAndEventId;
    }
}
