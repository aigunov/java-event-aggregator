package ru.practicum.statsserver;

import lombok.experimental.UtilityClass;
import ru.practicum.statsdto.dto.StatDto;

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

}
