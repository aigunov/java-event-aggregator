package ru.practicum.mainservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.mainservice.data.dto.*;
import ru.practicum.mainservice.data.model.*;
import ru.practicum.statsdto.dto.CategoryDto;
import ru.practicum.statsdto.dto.UserDto;

import java.util.Set;

@UtilityClass
public class Mapper {

    /**
     * UserDto --> User
     */
    public User toUser(final NewUserRequest dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    /**
     * User --> UserDto
     */
    public UserDto toUserDto(final User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }

    /**
     * CompilationDto --> Compilation
     */
    public Compilations toCompilations(final NewCompilationDto dto, Set<Event> events) {
        return Compilations.builder()
                .title(dto.getTitle())
                .pinned(dto.isPinned())
                .events(events)
                .build();
    }

    /**
     * Compilation, Events, CompilationDto --> CompilationDto for Updating
     */
    public Compilations toCompilationUpdate(final Compilations save, final Set<Event> events, final UpdateCompilationRequest dto) {
        save.setTitle(dto.getTitle() == null ? save.getTitle() : dto.getTitle());
        save.setEvents(events == null ? save.getEvents() : events);
        save.setPinned(dto.getPinned() == null ? save.isPinned() : dto.getPinned());
        return save;
    }

    /**
     * Category --> CategoryDto
     */
    public CategoryDto toCategoryDto(final Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    /**
     * CategoryDto --> Category
     */
    public Category toCategory(final CategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }


    /**
     * UpdateEvent, Event --> Event for Updating
     */
    public Event toEventUpdate(final Event event, final UpdateEventAdminRequest dto) {
        event.setAnnotation(dto.getAnnotation() == null ? event.getAnnotation() : dto.getAnnotation());
        event.setEventDate(dto.getEventDate() == null ? event.getEventDate() : dto.getEventDate());
        event.setPaid(dto.getPaid() == null ? event.getPaid() : dto.getPaid());
        event.setTitle(dto.getTitle() == null || dto.getTitle().isBlank() ? event.getTitle() : dto.getTitle());
        event.setDescription(dto.getDescription() == null ? event.getDescription() : dto.getDescription());
        event.setParticipantLimit(dto.getParticipantLimit() == null ? event.getParticipantLimit() : dto.getParticipantLimit());
        event.setLon(dto.getLocation() == null ? event.getLon() : dto.getLocation().lon());
        event.setLat(dto.getLocation() == null ? event.getLat() : dto.getLocation().lat());
        return event;
    }

    public static EventResponseDto toResponseDto(Event event) {
        return EventResponseDto.builder()
                .id(event.getId())
                .initiator(toUserDto(event.getInitiator()))
                .category(toCategoryDto(event.getCategory()))
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .paid(event.getPaid())
                .requestModeration(event.getRequestModeration())
                .participantLimit(event.getParticipantLimit())
                .state(event.getState())
                .location(new LocationDto(event.getLat(), event.getLon()))
                .createdOn(event.getCreatedOn())
                .eventDate(event.getEventDate())
                .build();
    }

    public static Event toEventCreate(final NewEventDto dto, final User initiator, final Category category) {
        return Event.builder()
                .initiator(initiator)
                .category(category)
                .title(dto.getTitle())
                .annotation(dto.getAnnotation())
                .description(dto.getDescription())
                .paid(dto.isPaid())
                .requestModeration(dto.isRequestModeration())
                .participantLimit(dto.getParticipantLimit())
                .eventDate(dto.getEventDate())
                .lon(dto.getLocation().getLon())
                .lat(dto.getLocation().getLat())
                .build();

    }

    public static Event toEventUpdate(final Event event, final UpdateEventUserRequest dto, Category category, User user) {
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            event.setTitle(dto.getTitle());
        }
        if (dto.getAnnotation() != null && !dto.getAnnotation().isBlank()) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getLocation() != null) {
            event.setLon(dto.getLocation().lon());
            event.setLat(dto.getLocation().lat());
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }

        return event;
    }

    public static RequestDto toRequestDto(final Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .requester(request.getRequester().getId())
                .userId(request.getRequester().getId())
                .eventId(request.getEvent().getId())
                .event(request.getEvent().getId())
                .created(request.getCreated())
                .status(request.getStatus())
                .build();
    }
}
