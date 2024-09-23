package ru.practicum.mainservice.controller.adminapi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.data.dto.event.EventResponseDto;
import ru.practicum.mainservice.data.dto.event.UpdateEventAdminRequest;
import ru.practicum.mainservice.data.model.RequestParameters;
import ru.practicum.mainservice.data.model.enums.States;
import ru.practicum.mainservice.service.interfaces.EventService;
import ru.practicum.mainservice.stat.StatAdapter;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminEventsController {
    private final EventService service;
    private final StatAdapter statAdapter;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/events")
    public List<EventResponseDto> getEvents(@RequestParam(required = false) final List<Long> users,
                                            @RequestParam(required = false) final List<String> states,
                                            @RequestParam(required = false) final List<Long> categories,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime rangeStart,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime rangeEnd,
                                            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") final Integer from,
                                            @PositiveOrZero @RequestParam(required = false, defaultValue = "10") final Integer size) {
        log.info("GET /events?users={}states={}categories={}rangeEnd={}rangeStart={}from={}size={}",
                users, states, categories, rangeEnd, rangeStart, from, size);
        List<States> statesEnum = states != null ?
                states.stream()
                        .map(States::valueOf)
                        .toList() : List.of();
        var events = service.adminSearchEvents(
                RequestParameters.builder()
                        .categories(categories)
                        .users(users)
                        .states(statesEnum)
                        .rangeEnd(rangeEnd)
                        .rangeStart(rangeStart)
                        .page(PageRequest.of(from / size, size))
                        .build()
        );
        statAdapter.setStatsForEvent(events);
        return events;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("admin/events/{eventId}")
    public EventResponseDto patchEvent(@PathVariable final Long eventId, @Valid @RequestBody final UpdateEventAdminRequest dto) {
        log.info("PUT /events/ <- {}", eventId);
        var event = service.adminUpdateEvent(eventId, dto);
        statAdapter.setStatsForEvent(List.of(event));
        return event;
    }
}
