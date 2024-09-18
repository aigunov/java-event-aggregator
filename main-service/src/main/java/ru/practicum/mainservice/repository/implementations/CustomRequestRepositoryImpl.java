package ru.practicum.mainservice.repository.implementations;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.data.model.Request;
import ru.practicum.mainservice.data.model.Status;
import ru.practicum.mainservice.repository.CustomRequestRepository;

import java.util.List;
import java.util.Set;

import static ru.practicum.mainservice.data.model.QRequest.request;

@Repository
@RequiredArgsConstructor
public class CustomRequestRepositoryImpl implements CustomRequestRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Request> getRequestByEvent(Long userId, Long eventId) {
        return queryFactory
                .select(request)
                .from(request)
                .where(request.event.id.eq(eventId)
                        .and(request.event.initiator.id.eq(userId)))
                .fetch();
    }

    @Override
    public List<Request> getPendingRequestsForEvent(Set<Long> requestIds, Long eventId) {
        return queryFactory.select(request).from(request)
                .where(request.event.id.eq(eventId)
                        .and(request.id.in(requestIds))
                        .and(request.status.eq(Status.PENDING)))
                .fetch();
    }

    @Override
    public Long getCountParticipants(Long eventId) {
        return queryFactory.select(request.count())
                .from(request)
                .where(request.event.id.eq(eventId)
                        .and(request.status.eq(Status.CONFIRMED)))
                .fetchOne();
    }

    @Override
    public Long getConfirmedCountListRequestOfEvent(Set<Long> requestIds, Long eventId) {
        return queryFactory.select(request.count())
                .from(request)
                .where(request.event.id.eq(eventId)
                        .and(request.id.in(requestIds))
                        .and(request.status.eq(Status.CONFIRMED)))
                .fetchOne();
    }
}
