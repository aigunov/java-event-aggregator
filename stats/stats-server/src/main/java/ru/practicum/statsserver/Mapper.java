package ru.practicum.statsserver;

import lombok.experimental.UtilityClass;
import ru.practicum.statsdto.dto.StatDto;
import ru.practicum.statsdto.dto.ViewStats;

@UtilityClass
public class Mapper {


    /**
     * StatisticDto --> Statistic
     */
    Statistic toStatistic(StatDto dto) {
        return Statistic.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(dto.getTimestamp())
                .build();
    }

    /**
     * Statistic --> StatisticDto
     */
    StatDto toStatDto(Statistic statistic) {
        return StatDto.builder()
                .ip(statistic.getIp())
                .uri(statistic.getUri())
                .timestamp(statistic.getTimestamp())
                .app(statistic.getApp())
                .build();
    }

    /**
     * Object --> ViewStats
     */
    ViewStats toViewStats(Object[] object) {
        return ViewStats.builder()
                .app((String) object[0])
                .uri((String) object[1])
                .hit((Long) object[2])
                .build();
    }
}
