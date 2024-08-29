package ru.practicum.statsserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statsdto.dto.StatDto;
import ru.practicum.statsdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public final class ServiceImpl implements StatService {
    private final StatisticsRepository repo;

    @Override
    public StatDto postHit(final StatDto dto) {
        Statistic stat = repo.save(Mapper.toStatistic(dto));
        log.info("saved statistic {}", stat);
        return Mapper.toStatDto(stat);
    }

    @Override
    public List<ViewStats> getViewStats(final LocalDateTime start, final LocalDateTime end,
                                        final List<String> uris, final Boolean unique) {
        List<ViewStats> viewStats = unique ?
                repo.getViewStatisticsUnique(start, end, uris).stream().map(Mapper::toViewStats).toList()
                : repo.getViewStatistics(start, end, uris).stream().map(Mapper::toViewStats).toList();
        log.info("viewStats {}", viewStats);
        return viewStats;
    }
}
