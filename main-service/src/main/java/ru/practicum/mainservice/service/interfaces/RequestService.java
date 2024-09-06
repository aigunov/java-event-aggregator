package ru.practicum.mainservice.service.interfaces;

import ru.practicum.statsdto.dto.RequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> getRequests(String userId);

    RequestDto createRequest(RequestDto dto);

    RequestDto cancelRequest(int userId, int requestId);
}
