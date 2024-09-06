package ru.practicum.mainservice.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.service.interfaces.UserService;
import ru.practicum.statsdto.dto.UserDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/users")
    public List<UserDto> getUsers(@RequestParam(required = false) final int[] ids,
                                  @RequestParam(required = false) final int from,
                                  @RequestParam(required = false) final int size) {
        log.info("PUT /users?ids={}, from={}, size={}", ids, from, size);
        return service.getUsers(ids, PageRequest.of(from / size, size));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/users")
    public UserDto createUser(@RequestBody final UserDto dto) {
        log.info("POST /admin/users <-- {}", dto);
        return service.createUser(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/users/{userId}")
    public void deleteUser(@PathVariable final int userId) {
        log.info("DELETE /admin/users/{}", userId);
        service.deleteUser(userId);
    }
}
