package ru.practicum.mainservice.repository.implementations;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.data.dto.event.EventResponseDto;
import ru.practicum.mainservice.data.dto.event.LocationDto;
import ru.practicum.mainservice.data.model.RequestParameters;
import ru.practicum.mainservice.data.model.enums.Status;
import ru.practicum.mainservice.repository.CustomEventRepository;
import ru.practicum.statsdto.dto.CategoryDto;
import ru.practicum.statsdto.dto.UserDto;

import java.util.List;

import static ru.practicum.mainservice.data.model.QCategory.category;
import static ru.practicum.mainservice.data.model.QEvent.event;
import static ru.practicum.mainservice.data.model.QRequest.request;
import static ru.practicum.mainservice.data.model.QUser.user;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<EventResponseDto> adminSearchEvents(final RequestParameters parameters) {
        JPAQuery<EventResponseDto> query = getJPQLQuery();

        if (!parameters.getStates().isEmpty()) {
            query.where(event.state.in(parameters.getStates()));
        }

        if (parameters.getUsers() != null && !parameters.getUsers().isEmpty()) {
            query.where(event.initiator.id.in(parameters.getUsers()));
        }

        if (parameters.getCategories() != null && !parameters.getCategories().isEmpty()) {
            query.where(event.category.id.in(parameters.getCategories()));
        }

        if (parameters.getRangeStart() != null) {
            query.where(event.eventDate.goe(parameters.getRangeStart()));
        }

        if (parameters.getRangeEnd() != null) {
            query.where(event.eventDate.loe(parameters.getRangeEnd()));
        }

        query.offset(parameters.getPage().getOffset())
                .limit(parameters.getPage().getPageSize());

        return query.fetch();
    }

    @Override
    public List<EventResponseDto> publicSearchEvents(final RequestParameters parameters) {
        JPAQuery<EventResponseDto> query = getJPQLQuery();

        if (parameters.getCategories() != null && !parameters.getCategories().isEmpty()) {
            query.where(event.category.id.in(parameters.getCategories()));
        }

        if (parameters.getText() != null && !parameters.getText().isBlank()) {
            String searchText = "%" + parameters.getText().trim() + "%";
            BooleanBuilder conditionForSearch = new BooleanBuilder();
            conditionForSearch.or(event.description.likeIgnoreCase(searchText))
                    .or(event.title.likeIgnoreCase(searchText))
                    .or(event.annotation.likeIgnoreCase(searchText));
            query.where(conditionForSearch);
        }

        if (parameters.getPaid() != null) {
            query.where(event.paid.eq(parameters.getPaid()));
        }

        if (parameters.getRangeStart() != null) {
            query.where(event.eventDate.goe(parameters.getRangeStart()));
        }

        if (parameters.getRangeEnd() != null) {
            query.where(event.eventDate.loe(parameters.getRangeEnd()));
        }

        if (parameters.getOnlyAvailable() != null && parameters.getOnlyAvailable()) {
            JPAQuery<Long> confirmedOnEventRequests = queryFactory
                    .select(request.count())
                    .from(request)
                    .where(request.event.id.eq(event.id)
                            .and(request.status.eq(Status.CONFIRMED)));

            query.where(confirmedOnEventRequests.lt(event.participantLimit));
        }
        query.offset(parameters.getPage().getOffset())
                .limit(parameters.getPage().getPageSize());
        return query.fetch();
    }

    private JPAQuery<EventResponseDto> getJPQLQuery() {

        return queryFactory.select(Projections.constructor(
                        EventResponseDto.class,
                        event.id,
                        Projections.constructor(UserDto.class, user.id, user.name, user.email),
                        Projections.constructor(CategoryDto.class, category.id, category.name),
                        Projections.constructor(LocationDto.class, event.lat, event.lon),
                        event.title,
                        event.annotation,
                        event.description,
                        event.participantLimit,
                        request.count(),
                        event.paid,
                        event.requestModeration,
                        event.state,
                        event.createdOn,
                        event.eventDate,
                        event.publishedOn,
                        Expressions.constant(0L)
                )).from(event)
                .leftJoin(event.initiator, user)
                .leftJoin(event.category, category)
                .leftJoin(request).on(request.event.id.eq(event.id)
                        .and(request.status.eq(Status.CONFIRMED)))
                .groupBy(event.id, user.id, category.id, event.lat, event.lon);
    }

}