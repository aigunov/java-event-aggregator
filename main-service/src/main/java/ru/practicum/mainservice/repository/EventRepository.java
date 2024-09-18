package ru.practicum.mainservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.mainservice.data.model.Category;
import ru.practicum.mainservice.data.model.Event;

import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {

    long countByCategory(Category category); // Метод подсчета событий по категории

    Page<Event> findEventByInitiatorId(final long userId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(final long eventId, final long initiatorId);

    @Query(value = """
            select * from events
            where events.id in :eventIds
            """, nativeQuery = true)
    Set<Event> findEventByIds(@Param("eventIds") final Set<Long> events);
}
