package ru.practicum.mainservice.controller.privateapi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.data.dto.*;
import ru.practicum.mainservice.data.dto.event.*;
import ru.practicum.mainservice.service.interfaces.EventService;
import ru.practicum.mainservice.service.interfaces.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PrivateEventsController {
    private final EventService service;
    private final RequestService requestService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userId}/events")
    public List<EventResponseDto> getUsersEvents(@PathVariable("userId") final Long userId,
                                                 @RequestParam(required = false, defaultValue = "0") @PositiveOrZero final int from,
                                                 @RequestParam(required = false, defaultValue = "10") @PositiveOrZero final int size) {
        log.info("GET /users/{}/events?from={}, size={}", userId, from, size);
        return service.getUserEvents(userId, PageRequest.of(from / size, size));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/events")
    public EventResponseDto postEvent(@Valid @RequestBody final NewEventDto dto,
                                      @PathVariable final Long userId) {
        log.info("POST /users/{}/", userId);
        return service.createEvent(userId, dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userId}/events/{eventId}")
    public EventResponseDto getEvent(@PositiveOrZero @PathVariable final Long userId,
                                     @PositiveOrZero @PathVariable final Long eventId) {
        log.info("GET /users/{}/events/{}", userId, eventId);
        return service.getEvent(userId, eventId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventResponseDto patchEvent(@PositiveOrZero @PathVariable final Long userId,
                                       @PositiveOrZero @PathVariable final Long eventId,
                                       @Valid @RequestBody final UpdateEventUserRequest eventUpdateDto) {
        log.info("PATCH /users/{}/events/{}", userId, eventId);
        eventUpdateDto.setId(eventId);
        eventUpdateDto.setInitiator(userId);
        return service.userUpdateEvent(eventUpdateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<RequestDto> getRequests(@PositiveOrZero @PathVariable final Long userId,
                                        @PositiveOrZero @PathVariable final Long eventId) {
        log.info("GET /users/{}/events/requests?eventId={}", userId, eventId);
        return requestService.getEventsRequests(userId, eventId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult patchRequests(@PositiveOrZero @PathVariable final Long userId,
                                                        @PositiveOrZero @PathVariable final Long eventId,
                                                        @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("PATCH /users/{}/events/{}", userId, eventId);
        return requestService.eventCreaterUpdateRequest(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
