package ru.practicum.mainservice.stat;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.data.dto.EventResponseDto;
import ru.practicum.statsdto.dto.ViewStats;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventStatConverter {
    public static List<EventResponseDto> convertToEventWithStatistic(List<EventResponseDto> events,
                                                                     Map<Long, ViewStats> stats) {
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
