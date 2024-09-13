package ru.practicum.mainservice.service.implemetations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.data.dto.EventResponseDto;
import ru.practicum.mainservice.data.dto.NewEventDto;
import ru.practicum.mainservice.data.dto.UpdateEventAdminRequest;
import ru.practicum.mainservice.data.dto.UpdateEventUserRequest;
import ru.practicum.mainservice.data.model.*;
import ru.practicum.mainservice.mapper.Mapper;
import ru.practicum.mainservice.repository.CategoriesRepository;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.repository.RequestRepository;
import ru.practicum.mainservice.repository.UserRepository;
import ru.practicum.mainservice.service.interfaces.EventService;
import ru.practicum.mainservice.stat.StatAdapter;
import ru.practicum.statsdto.dto.URLParameter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final CategoriesRepository categoriesRepository;
    private final StatAdapter adapter;

    @Override
    public EventResponseDto adminUpdateEvent(Long eventId, final UpdateEventAdminRequest dto) {
        Event event = repository.findById(eventId).orElseThrow(NoSuchElementException::new);
        if (dto.getEventDate() != null
                && dto.getEventDate().isBefore(LocalDateTime.now().minusHours(1))) {
            throw new NoValidParameter("Дата начала изменяемого события должна быть не ранее чем за час от даты публикации");
        }

        if (dto.getStateAction() == UpdateEventAdminRequest.StateActionAdmin.PUBLISH_EVENT
                && event.getState() != States.PENDING) {
            throw new EntityUpdateConflict("Событие можно публиковать, только если оно в состоянии ожидания публикации");
        }

        if (dto.getStateAction() == UpdateEventAdminRequest.StateActionAdmin.REJECT_EVENT
                && event.getState() == States.PUBLISHED) {
            throw new EntityUpdateConflict("Событие можно отклонить, только если оно еще не опубликовано");
        }

        if (dto.getStateAction() != null) {
            if (dto.getStateAction() == UpdateEventAdminRequest.StateActionAdmin.PUBLISH_EVENT) {
                event.setState(States.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else {
                event.setState(States.CANCELED);
            }
        }

        event = repository.save(Mapper.toEventUpdate(event, dto));
        log.info("Admin updated event: {}", event);

        EventResponseDto responseDto = Mapper.toResponseDto(event);
        responseDto.setConfirmedRequests(requestRepository.getCountParticipants(eventId));
        return responseDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventResponseDto> adminSearchEvents(final RequestParameters params) {
        var list = repository.adminSearchEvents(params);
        log.info("Admin found events: {}", list);
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventResponseDto> userSearchEvents(RequestParameters params) {
        var list = repository.publicSearchEvents(params);
        log.info("User found events: {}", list);
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public EventResponseDto getEvent(final Long id) {
        var event = repository.findById(id).orElseThrow(NoSuchElementException::new);
        if (event.getState() != States.PUBLISHED) {
            throw new NoSuchElementException("Не найден такой event ли он еще не опубликован");
        }
        var params = URLParameter.builder()
                .start(LocalDateTime.now().minusDays(1))
                .end(LocalDateTime.now().plusDays(1))
                .uris(List.of("/events/" + event.getId()))
                .build();
        var resp = Mapper.toResponseDto(event);
        resp.setViews((long) adapter.getStats(params).size());
        return resp;
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventResponseDto> getUserEvents(final Long userId, final PageRequest of) {
        var events = repository.findEventByInitiatorId(userId, of).stream().map(Mapper::toResponseDto).toList();
        log.info("User found events: {}", events);
        return events;
    }

    @Override
    public EventResponseDto createEvent(final Long userId, final NewEventDto dto) {
        var initiator = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        var category = categoriesRepository.findById(dto.getCategory()).orElseThrow(NoSuchElementException::new);
        var event = Mapper.toEventCreate(dto, initiator, category);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(States.PENDING);
        event.setPublishedOn(LocalDateTime.now());
        event = repository.save(event);
        log.info("Created event: {}", event);
        return Mapper.toResponseDto(event);
    }

    @Transactional(readOnly = true)
    @Override
    public EventResponseDto getEvent(final Long userId, final Long eventId) {
        Event event = repository.findById(eventId).orElseThrow(NoSuchElementException::new);
        if (event.getInitiator().getId() != userId) {
            throw new NoSuchElementException("User don't have Event with id=" + eventId);
        }
        return Mapper.toResponseDto(event);
    }

    @Override
    public EventResponseDto userUpdateEvent(final UpdateEventUserRequest eventUpdateDto) {
        Event event = repository.findById(eventUpdateDto.getId()).orElseThrow(NoSuchElementException::new);
        Category category = null;
        User user = userRepository.findById(eventUpdateDto.getInitiator()).orElseThrow(NoSuchElementException::new);

        if (eventUpdateDto.getCategory() != null) {
            category = categoriesRepository.findById(eventUpdateDto.getCategory()).get();
        }

        if (!Long.valueOf(event.getInitiator().getId()).equals(eventUpdateDto.getInitiator())) {
            throw new NoSuchElementException("Event with id=" + eventUpdateDto.getId() + "was not found");
        }

        if (event.getState() == States.PUBLISHED) {
            throw new EntityUpdateConflict("Нельзя изменять опубликованное событие");
        }

        if (eventUpdateDto.getEventDate() != null && eventUpdateDto.getEventDate().isBefore(LocalDateTime.now().minusHours(2))) {
            throw new NoValidParameter("дата и время на которые намечено событие не может быть раньше," +
                    " чем через два часа от текущего момента");
        }

        if (eventUpdateDto.getStateAction() != null) {
            event.setState(eventUpdateDto.getStateAction() == UpdateEventUserRequest.StateActionUser.CANCEL_REVIEW
                    ? States.CANCELED
                    : States.PENDING);
        }
        event = repository.save(Mapper.toEventUpdate(event, eventUpdateDto, category, user));
        return Mapper.toResponseDto(event);
    }

}
