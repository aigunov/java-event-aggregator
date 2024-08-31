package ru.practicum.statsserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistic, Long> {

    @Query(value = """
            select st.app, st.uri, count(st.ip)
            from statistics as st
            where st.timestamp between :start and :end
            and (
                :uris is null or
                st.uri IN (:uris))
            group by st.app, st.uri
            """, nativeQuery = true)
    List<Object[]> getViewStatistics(@Param("start") final LocalDateTime start,
                                     @Param("end") final LocalDateTime end,
                                     @Param("uris") final List<String> uris);

    @Query(value = """
            select st.app, st.uri, count(distinct st.ip)
            from statistics as st
            where st.timestamp between :start and :end
            and (
                :uris is null or
                st.uri IN (:uris))
            group by st.app, st.uri
            """, nativeQuery = true)
    List<Object[]> getViewStatisticsUnique(@Param("start") final LocalDateTime start,
                                           @Param("end") final LocalDateTime end,
                                           @Param("uris") final List<String> uris);
}
