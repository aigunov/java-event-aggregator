package ru.practicum.mainservice.controller.publicapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.model.RequestParametersDTO;
import ru.practicum.mainservice.service.interfaces.CompilationsService;
import ru.practicum.statsdto.dto.CompilationsDto;

import java.util.List;

@Slf4j
@RestController("/compilations")
@RequiredArgsConstructor
public class PublicCompilationsController {
    private final CompilationsService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CompilationsDto> getCompilations(@RequestParam(required = false) final boolean pinned,
                                                 @RequestParam(required = false) final int from,
                                                 @RequestParam(required = false) final int size) {
        log.info("GET /compilations");
        return service.getCompilations(
                RequestParametersDTO.builder()
                        .pinned(pinned)
                        .page(PageRequest.of(from / size, size))
                        .build()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{compId}")
    public CompilationsDto getCompilation(@PathVariable final int compId) {
        log.info("GET /compilations/{compId}");
        return service.getCompilation(compId);
    }
}
