package ru.practicum.mainservice.service.implemetations;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.data.dto.comment.CommentDto;
import ru.practicum.mainservice.data.dto.comment.CommentStatusUpdateRequest;
import ru.practicum.mainservice.data.dto.comment.CommentStatusUpdateResult;
import ru.practicum.mainservice.data.model.enums.CommentStatus;
import ru.practicum.mainservice.data.model.enums.States;
import ru.practicum.mainservice.data.model.enums.Status;
import ru.practicum.mainservice.data.model.exceptions.CommentActionException;
import ru.practicum.mainservice.mapper.Mapper;
import ru.practicum.mainservice.repository.CommentRepository;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.repository.RequestRepository;
import ru.practicum.mainservice.repository.UserRepository;
import ru.practicum.mainservice.service.interfaces.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.mainservice.data.dto.comment.CommentStatusUpdateRequest.Status.APPROVE;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;


    @Override
    public CommentStatusUpdateResult decisionComments(Long eventId, CommentStatusUpdateRequest commentStatusUpdateRequest) {

        eventRepository.findById(eventId).orElseThrow(NoSuchElementException::new);

        List<CommentDto> comments = commentRepository.getCommentsByIdsAndEventId(commentStatusUpdateRequest.getIds(), eventId)
                .stream().map(Mapper::toCommentDto).toList();

        if (commentStatusUpdateRequest.getStatus() == APPROVE
                && comments.stream().anyMatch(c -> c.getStatus() == CommentStatus.APPROVED)) {
            throw new CommentActionException("Нельзя дважды одобрить публикацию комментария");
        }

        if (commentStatusUpdateRequest.getStatus() == CommentStatusUpdateRequest.Status.REJECT
                && comments.stream().anyMatch(c -> c.getStatus() == CommentStatus.REJECTED)) {
            throw new CommentActionException("Нельзя дважды отклонить публикацию комментария");
        }

        CommentStatus updatedStatus = commentStatusUpdateRequest.getStatus() == APPROVE
                ? CommentStatus.APPROVED
                : CommentStatus.REJECTED;

        Set<Long> commentsIds = comments.stream()
                .peek(c -> c.setStatus(updatedStatus))
                .map(CommentDto::getId).collect(Collectors.toSet());

        if (updatedStatus == CommentStatus.APPROVED) {
            commentRepository.publishComment(LocalDateTime.now(), commentsIds.stream().toList());
        } else {
            commentRepository.rejectComment(commentsIds.stream().toList());
        }
        CommentStatusUpdateResult commentStatusUpdateResult = Mapper.concertToUpdatedResult(comments);
        log.info("Принято решение по следующим комментариям: {}\nРезультат: {}", commentStatusUpdateResult, commentStatusUpdateResult);
        return commentStatusUpdateResult;
    }

    @Override
    public CommentDto createComment(Long userId, Long eventId, CommentDto dto) {
        var user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        var event = eventRepository.findById(eventId).orElseThrow(NoSuchElementException::new);

        if(event.getState()!= States.PUBLISHED){
            throw new CommentActionException("Комментировать неопубликованное событие невозможно");
        }

        if(event.getInitiator().getId()==user.getId()){
            dto.setStatus(CommentStatus.APPROVED);
            dto.setPublishedOn(LocalDateTime.now());
        }else{
            if (!hasConfirmedRequestOnEvent(userId, eventId)){
                throw new CommentActionException("Пользователь не может оставлять комментарий не приняв участие в событии.");
            }
            dto.setStatus(CommentStatus.PENDING);
        }

        var comment = commentRepository.save(Mapper.toComment(dto, user, event));
        log.info("Comment saved: {}", comment);
        return Mapper.toCommentDto(comment);
    }

    @Override
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        var event = eventRepository.findById(eventId).orElseThrow(NoSuchElementException::new);
        var comment = commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);

        if(userId != comment.getAuthor().getId() && userId != event.getInitiator().getId()){
            throw new CommentActionException("Комментарий могут удалять или автор или инициатор события");
        }

        commentRepository.delete(comment);
        log.info("Comment deleted: {}", comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getCommentsByEventId(Long userId, Long eventId, PageRequest of) {
        var user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        var event = eventRepository.findById(eventId).orElseThrow(NoSuchElementException::new);

        if (!(user.getId() == (event.getInitiator().getId()))) {
            throw new CommentActionException("Нельзя просмотреть все комментариии если вы не владаелец события");
        }
        List<CommentDto> commentsByEventId = commentRepository.getCommentsByEventId(eventId, of)
                .stream().map(Mapper::toCommentDto).toList();
        log.info("Найдены комментарии: {}", commentsByEventId);
        return commentsByEventId;
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto getCommentByIdAndEventId(Long userId, Long eventId, Long commentId) {
        userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        eventRepository.findById(eventId).orElseThrow(NoSuchElementException::new);
        var comment = commentRepository.findByEventIdAndId(eventId, commentId)
                .orElseThrow(NoSuchElementException::new);

        log.info("Comment get: {}", comment);
        return Mapper.toCommentDto(comment);
    }


    @Transactional(readOnly = true)
    @Override
    public CommentDto findCommentById(Long commentId) {
        var dto = Mapper.toCommentDto(commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new));
        log.info("Comment found: {}", dto);
        return dto;
    }

    private boolean hasConfirmedRequestOnEvent(final long userId, final long eventId){
        return requestRepository.existsByRequesterIdAndEventIdAndStatusIn(
                userId,
                eventId,
                List.of(Status.CONFIRMED)
        );
    }
}
