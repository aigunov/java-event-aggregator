package ru.practicum.mainservice.controller.privateapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.service.interfaces.RequestService;
import ru.practicum.statsdto.dto.RequestDto;

import java.util.List;

@Slf4j
@RestController("users")
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}/requests")
    public List<RequestDto> getRequests(@PathVariable("userId") String userId) {
        log.info("GET /users/{userId}/requests");
        return service.getRequests(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/requests")
    public RequestDto createRequest(@PathVariable("userId") final int userId,
                                    @RequestBody final RequestDto dto) {
        log.info("POST /users/{}/requests <-- {}", userId, dto);
        dto.setUserId(userId);
        return service.createRequest(dto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable final int userId,
                                    @PathVariable final int requestId) {
        log.info("PATCH /users/{}/requests/{}", userId, requestId);
        return service.cancelRequest(userId, requestId);
    }

}
