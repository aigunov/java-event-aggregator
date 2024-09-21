package ru.practicum.mainservice.service.interfaces;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import ru.practicum.mainservice.data.dto.comment.CommentDto;
import ru.practicum.mainservice.data.dto.comment.CommentStatusUpdateRequest;
import ru.practicum.mainservice.data.dto.comment.CommentStatusUpdateResult;

import java.util.List;

public interface CommentService {

    CommentDto findCommentById(Long commentId);

    CommentStatusUpdateResult decisionComments(Long eventId, CommentStatusUpdateRequest commentStatusUpdateRequest);

    CommentDto createComment(Long userId, Long eventId, CommentDto comment);

    void deleteComment(Long userId, Long eventId, Long commentId);

    List<CommentDto> getCommentsByEventId(Long userId, Long eventId, PageRequest of);

    CommentDto getCommentByIdAndEventId(Long userId, Long eventId, Long commentId);
}
