package ru.practicum.mainservice.service.implemetations;

import org.springframework.stereotype.Service;
import ru.practicum.mainservice.service.interfaces.RequestService;
import ru.practicum.statsdto.dto.RequestDto;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Override
    public List<RequestDto> getRequests(String userId) {
        return List.of();
    }

    @Override
    public RequestDto createRequest(RequestDto dto) {
        return null;
    }

    @Override
    public RequestDto cancelRequest(int userId, int requestId) {
        return null;
    }
}
