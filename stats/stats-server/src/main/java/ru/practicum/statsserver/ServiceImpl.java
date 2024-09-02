package ru.practicum.statsserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statsdto.dto.StatDto;
import ru.practicum.statsdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
        List<ViewStats> viewStatsList = new ArrayList<>();
        if (uris == null) {
            if (unique) {
                viewStatsList = repo.getAllViewStats(start, end);
            } else {
                viewStatsList = repo.getAllViewStatsUnique(start, end);
            }
        } else {
            if (unique) {
                for (String s : uris) {
                    ViewStats viewStats = repo.getViewStatsUnique(start, end, s);
                    viewStatsList.add(Objects.requireNonNullElseGet(viewStats, () -> new ViewStats("ewm-main-service", s, 0L)));
                }
            } else {
                for (String s : uris) {
                    ViewStats viewStats = repo.getViewStats(start, end, s);
                    viewStatsList.add(Objects.requireNonNullElseGet(viewStats, () -> new ViewStats("ewm-main-service", s, 0L)));
                }
            }
        }
        viewStatsList.sort(Comparator.comparing(ViewStats::getHits).reversed());
        log.info("GET /stats -> returning from db {}", viewStatsList);
        return viewStatsList;
    }
}
