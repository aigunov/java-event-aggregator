package ru.practicum.mainservice.controller.privateapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.RequestParameters;
import ru.practicum.mainservice.service.interfaces.EventService;
import ru.practicum.statsdto.dto.EventDto;
import ru.practicum.statsdto.dto.RequestDto;

import java.util.List;

@Slf4j
@RestController("/users")
@RequiredArgsConstructor
public class PrivateEventsController {
    private final EventService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}/events")
    public List<EventDto> getEvents(@PathVariable("userId") final int userId,
                                    @RequestParam(required = false) final int from,
                                    @RequestParam(required = false) final int size) {
        log.info("GET /users/{}/events?from={}, size={}", userId, from, size);
        return service.getUserEvents(
                RequestParameters.builder()
                        .page(PageRequest.of(from / size, size))
                        .userId(userId)
                        .build()
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/events")
    public EventDto postEvent(@RequestBody final EventDto dto,
                              @PathVariable final int userId) {
        log.info("POST /users/{}/", userId);
        dto.setUserId(userId);
        return service.createEvent(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}/events/{eventId}")
    public EventDto getEvent(@PathVariable final int userId,
                             @PathVariable final int eventId) {
        log.info("GET /users/{}/events/{}", userId, eventId);
        return service.getEvent(userId, eventId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{userId}/events/{eventId}")
    public EventDto patchEvent(@PathVariable final int userId,
                               @PathVariable final int eventId) {
        log.info("PATCH /users/{}/events/{}", userId, eventId);
        return service.userUpdateEvent(userId, eventId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestDto> getRequests(@PathVariable final int userId,
                                        @PathVariable final int eventId) {
        log.info("GET /users/{}/events/requests?eventId={}", userId, eventId);
        return service.getEventsRequests(userId, eventId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{userId}/events/{eventId}/requests")
    public List<RequestDto> patchRequests(@PathVariable final int userId,
                                          @PathVariable final int eventId) {
        log.info("PATCH /users/{}/events/{}", userId, eventId);
        return service.eventCreaterUpdateRequest(userId, eventId);
    }
}
