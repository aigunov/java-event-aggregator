package ru.practicum.statsserver;

import ru.practicum.statsdto.dto.StatDto;
import ru.practicum.statsdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    StatDto postHit(StatDto dto);

    List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
