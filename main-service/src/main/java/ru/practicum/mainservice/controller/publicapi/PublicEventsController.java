package ru.practicum.mainservice.controller.publicapi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.data.dto.EventResponseDto;
import ru.practicum.mainservice.data.model.RequestParameters;
import ru.practicum.mainservice.data.model.Sort;
import ru.practicum.mainservice.service.interfaces.EventService;
import ru.practicum.mainservice.stat.StatAdapter;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PublicEventsController {
    private final EventService service;
    private final StatAdapter statAdapter;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/events")
    public List<EventResponseDto> getEvents(@RequestParam(required = false) final String text,
                                            @RequestParam(required = false) final List<Long> categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime rangeStart,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime rangeEnd,
                                            @RequestParam(required = false) Boolean onlyAvailable,
                                            @RequestParam(required = false) final Sort sort,
                                            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") final Integer from,
                                            @PositiveOrZero @RequestParam(required = false, defaultValue = "10") final Integer size,
                                            HttpServletRequest request) {
        log.info("GET /events?categories={}paid={}rangeStart={}rangeEnd={}onlyAvailable={}sort={}from={}size={}",
                categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        var params = RequestParameters.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeEnd(rangeEnd)
                .rangeStart(rangeStart)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .page(PageRequest.of(from / size, size))
                .build();
        params.checkValid();
        var events = service.userSearchEvents(params);
        statAdapter.sendStats(request);
        statAdapter.setStatsForEvent(events);
        return events;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/events/{id}")
    public EventResponseDto getEvent(@PathVariable final Long id, HttpServletRequest request) {
        log.info("GET /events/{}", id);
        var event = service.getEvent(id);
        statAdapter.sendStats(request);
        statAdapter.setStatsForEvent(List.of(event));
        return event;
    }
}
