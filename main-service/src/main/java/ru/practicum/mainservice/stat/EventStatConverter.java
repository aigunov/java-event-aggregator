package ru.practicum.mainservice.stat;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.mainservice.data.dto.EventResponseDto;
import ru.practicum.statsdto.dto.ViewStats;

import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventStatConverter {
    public static List<EventResponseDto> convertToEventWithStatistic(List<EventResponseDto> events,
                                                                     Map<Long, ViewStats> stats) {
        log.info("Converting {} events", events.size());
        for (EventResponseDto event : events) {
            ViewStats statistic = stats.get(event.getId());
            if (statistic == null) {
                event.setViews(0L);
                continue;
            }
            Long views = statistic.getHits();
            event.setViews(views);
        }
        return events;
    }
}
