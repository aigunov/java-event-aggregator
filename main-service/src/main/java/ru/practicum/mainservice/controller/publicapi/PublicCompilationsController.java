package ru.practicum.mainservice.controller.publicapi;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.data.model.Compilations;
import ru.practicum.mainservice.service.interfaces.CompilationsService;

import java.util.List;

/**
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PublicCompilationsController {
    private final CompilationsService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/compilations")
    public List<Compilations> getCompilations(@RequestParam(required = false) Boolean pinned,
                                              @PositiveOrZero @RequestParam(required = false, defaultValue = "0") final Integer from,
                                              @PositiveOrZero @RequestParam(required = false, defaultValue = "10") final Integer size) {
        log.info("GET /compilations");
        return service.getCompilations(pinned, PageRequest.of(from / size, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/compilations/{compId}")
    public Compilations getCompilation(@PositiveOrZero @PathVariable final Long compId) {
        log.info("GET /compilations/{compId}");
        return service.getCompilation(compId);
    }
}
