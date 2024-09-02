package ru.practicum.statsserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.statsdto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistic, Long> {

    @Query("SELECT new ru.practicum.statsdto.dto.ViewStats(eh.app, eh.uri, count(eh.id)) FROM Statistic AS eh WHERE eh.timestamp BETWEEN ?1 AND ?2 AND eh.uri = ?3 GROUP BY eh.app, eh.uri")
    ViewStats getViewStats(LocalDateTime start, LocalDateTime end, String uri);

    @Query("SELECT new ru.practicum.statsdto.dto.ViewStats(eh.app, eh.uri, count(DISTINCT eh.ip)) FROM Statistic AS eh WHERE eh.timestamp BETWEEN ?1 AND ?2 AND eh.uri = ?3 GROUP BY eh.app, eh.uri")
    ViewStats getViewStatsUnique(LocalDateTime start, LocalDateTime end, String uri);

    @Query("SELECT new ru.practicum.statsdto.dto.ViewStats(eh.app, eh.uri, count(eh.id)) FROM Statistic AS eh WHERE eh.timestamp BETWEEN ?1 AND ?2 GROUP BY eh.app, eh.uri")
    List<ViewStats> getAllViewStats(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.statsdto.dto.ViewStats(eh.app, eh.uri, count(DISTINCT eh.ip)) FROM Statistic AS eh WHERE eh.timestamp BETWEEN ?1 AND ?2 GROUP BY eh.app, eh.uri")
    List<ViewStats> getAllViewStatsUnique(LocalDateTime start, LocalDateTime end);
}
