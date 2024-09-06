package ru.practicum.mainservice.service.implemetations;

import org.springframework.stereotype.Service;
import ru.practicum.mainservice.model.RequestParametersDTO;
import ru.practicum.mainservice.service.interfaces.EventService;
import ru.practicum.statsdto.dto.EventDto;
import ru.practicum.statsdto.dto.RequestDto;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Override
    public EventDto adminUpdateEvent(EventDto dto) {
        return null;
    }

    @Override
    public List<EventDto> adminSearchEvents(RequestParametersDTO build) {
        return List.of();
    }

    @Override
    public List<EventDto> getUserEvents(RequestParametersDTO build) {
        return List.of();
    }

    @Override
    public EventDto createEvent(EventDto dto) {
        return null;
    }

    @Override
    public EventDto getEvent(int userId, int eventId) {
        return null;
    }

    @Override
    public EventDto userUpdateEvent(int userId, int eventId) {
        return null;
    }

    @Override
    public List<RequestDto> getEventsRequests(int userId, int eventId) {
        return List.of();
    }

    @Override
    public List<RequestDto> eventCreaterUpdateRequest(int userId, int eventId) {
        return List.of();
    }

    @Override
    public List<EventDto> userSearchEvents(RequestParametersDTO build) {
        return List.of();
    }

    @Override
    public EventDto getEvent(int id) {
        return null;
    }
}
