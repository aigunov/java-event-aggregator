package ru.practicum.mainservice.controller.adminapi;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.data.dto.NewUserRequest;
import ru.practicum.mainservice.service.interfaces.UserService;
import ru.practicum.statsdto.dto.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/users")
    public List<UserDto> getUsers(@RequestParam(required = false) final Optional<List<Long>> ids,
                                  @PositiveOrZero @RequestParam(required = false, defaultValue = "0") final Integer from,
                                  @PositiveOrZero @RequestParam(required = false, defaultValue = "10") final Integer size) {
        log.info("GET /users?ids={}, from={}, size={}", ids, from, size);
        return service.getUsers(ids.orElse(Collections.emptyList()), PageRequest.of(from / size, size));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/users")
    public UserDto createUser(@Valid @RequestBody final NewUserRequest dto) {
        log.info("POST /admin/users <-- {}", dto);
        return service.createUser(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/users/{userId}")
    public void deleteUser(@Min(1) @PathVariable final Long userId) {
        log.info("DELETE /admin/users/{}", userId);
        service.deleteUser(userId);
    }
}
