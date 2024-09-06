package ru.practicum.mainservice.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.service.interfaces.CompilationsService;
import ru.practicum.statsdto.dto.CompilationsDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCompilationsController {
    private final CompilationsService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/compilations")
    public CompilationsDto postCompilations(@RequestBody final CompilationsDto dto) {
        log.info("POST /compilations <- {}", dto);
        return service.createCompilation(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/compilations/{compId}")
    public void deleteCompilations(@PathVariable final int compId) {
        log.info("DELETE /compilations/{compId} <- {}", compId);
        service.deleteCompilation(compId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/admin/compilations/{compId}")
    public CompilationsDto updateCompilations(@PathVariable final int compId,
                                              @RequestBody final CompilationsDto dto) {
        log.info("PATCH /compilations/{compId} <- {}", compId);
        dto.setId(compId);
        return service.updateCompilation(dto);
    }
}
