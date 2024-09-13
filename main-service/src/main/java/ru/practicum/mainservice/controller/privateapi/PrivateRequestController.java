package ru.practicum.mainservice.controller.privateapi;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.data.dto.RequestDto;
import ru.practicum.mainservice.service.interfaces.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userId}/requests")
    public List<RequestDto> getRequests(@PositiveOrZero @PathVariable("userId") final Long userId) {
        log.info("GET /users/{userId}/requests");
        return service.getRequests(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/requests")
    public RequestDto createRequest(@PathVariable("userId") final Long userId,
                                    @PositiveOrZero @RequestParam("eventId") final Long eventId) {
        log.info("POST /users/{}/requests <-- {}", userId, eventId);
        return service.createRequest(userId, eventId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable final Long userId,
                                    @PathVariable final Long requestId) {
        log.info("PATCH /users/{}/requests/{}", userId, requestId);
        return service.cancelRequest(userId, requestId);
    }

}
