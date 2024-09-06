package ru.practicum.mainservice.controller.adminapi;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.EventStates;
import ru.practicum.mainservice.model.RequestParametersDTO;
import ru.practicum.mainservice.service.interfaces.EventService;
import ru.practicum.statsdto.dto.EventDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminEventsController {
    private final EventService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/events")
    public List<EventDto> getEvents(@RequestParam(required = false) final int @NonNull [] users,
                                    @RequestParam(required = false) final EventStates[] @NonNull states,
                                    @RequestParam(required = false) final int[] categories,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime rangeStart,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime rangeEnd,
                                    @RequestParam(required = false) final int from,
                                    @RequestParam(required = false) final int size) {
        log.info("GET /events?users={}states={}categories={}rangeEnd={}rangeStart={}from={}size={}",
                users, states, categories, rangeEnd, rangeStart, from, size);

        return service.adminSearchEvents(
                RequestParametersDTO.builder()
                        .categories(categories)
                        .users(users)
                        .states(states)
                        .rangeEnd(rangeEnd)
                        .rangeStart(rangeStart)
                        .page(PageRequest.of(from / size, size))
                        .build()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/events/{eventId}")
    public EventDto patchEvent(@PathVariable final int eventId, @RequestBody final EventDto dto) {
        log.info("PUT /events/ <- {}", eventId);
        dto.setId(eventId);
        return service.adminUpdateEvent(dto);
    }
}
