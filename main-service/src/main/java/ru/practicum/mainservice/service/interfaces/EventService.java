package ru.practicum.mainservice.service.interfaces;

import ru.practicum.mainservice.model.RequestParameters;
import ru.practicum.statsdto.dto.EventDto;
import ru.practicum.statsdto.dto.RequestDto;

import java.util.List;

public interface EventService {
    EventDto adminUpdateEvent(EventDto dto);

    List<EventDto> adminSearchEvents(RequestParameters build);

    List<EventDto> getUserEvents(RequestParameters build);

    EventDto createEvent(EventDto dto);

    EventDto getEvent(int userId, int eventId);

    EventDto userUpdateEvent(int userId, int eventId);

    List<RequestDto> getEventsRequests(int userId, int eventId);

    List<RequestDto> eventCreaterUpdateRequest(int userId, int eventId);

    List<EventDto> userSearchEvents(RequestParameters build);

    EventDto getEvent(int id);
}
