package ru.practicum.mainservice.controller.publicapi;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.RequestParametersDTO;
import ru.practicum.mainservice.model.Sort;
import ru.practicum.mainservice.service.interfaces.EventService;
import ru.practicum.statsdto.dto.EventDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController("/events")
@RequiredArgsConstructor
public class PublicEventsController {
    private final EventService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<EventDto> getEvents(@RequestParam(required = false) final @NonNull String text,
                                    @RequestParam(required = false) final int[] categories,
                                    @RequestParam(required = false) final boolean paid,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime rangeStart,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime rangeEnd,
                                    @RequestParam(required = false) final boolean onlyAvailable,
                                    @RequestParam(required = false) final Sort sort,
                                    @RequestParam(required = false) final int from,
                                    @RequestParam(required = false) final int size) {
        log.info("GET /events?text={}categories={}paid={}rangeStart={}rangeEnd={}onlyAvailable={}sort={}from={}size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return service.userSearchEvents(
                RequestParametersDTO.builder()
                        .text(text)
                        .categories(categories)
                        .paid(paid)
                        .rangeEnd(rangeEnd)
                        .rangeStart(rangeStart)
                        .onlyAvailable(onlyAvailable)
                        .sort(sort)
                        .page(PageRequest.of(from / size, size))
                        .build()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable final int id) {
        log.info("GET /events/{}", id);
        return service.getEvent(id);
    }
}
