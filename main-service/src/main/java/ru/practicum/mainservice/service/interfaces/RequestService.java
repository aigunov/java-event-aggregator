package ru.practicum.mainservice.service.interfaces;

import jakarta.validation.Valid;
import ru.practicum.mainservice.data.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.data.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.data.dto.RequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> getRequests(final Long userId);

    RequestDto createRequest(final Long userId, final Long eventId);

    RequestDto cancelRequest(final Long userId, final Long requestId);

    List<RequestDto> getEventsRequests(final Long userId, final Long eventId);

    EventRequestStatusUpdateResult eventCreaterUpdateRequest(final Long userId, final Long eventId, @Valid EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
