package ru.practicum.statsserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsdto.dto.StatDto;
import ru.practicum.statsdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * RestController
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public final class Controller {
    private final StatService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public StatDto postHit(@RequestBody final StatDto dto) {
        dto.setTimestamp(LocalDateTime.now());
        log.info("POST /hit <- {}", dto);
        return service.postHit(dto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStats> getViewStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime start,
                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime end,
                                        @RequestParam(required = false, defaultValue = "[]") final List<String> uris,
                                        @RequestParam(defaultValue = "false") final Boolean unique) {
        log.info("GET /stats <- with start={}, end={} uri={} unique={}", start, end, uris, unique);
        return service.getViewStats(start, end, uris, unique);
    }

}

