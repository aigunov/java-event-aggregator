package ru.practicum.mainservice.controller.adminapi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.data.dto.compilation.NewCompilationDto;
import ru.practicum.mainservice.data.dto.compilation.UpdateCompilationRequest;
import ru.practicum.mainservice.data.model.Compilations;
import ru.practicum.mainservice.service.interfaces.CompilationsService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCompilationsController {
    private final CompilationsService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/compilations")
    public Compilations postCompilations(@Valid @RequestBody final NewCompilationDto dto) {
        log.info("POST /compilations <- {}", dto);
        return service.createCompilation(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/compilations/{compId}")
    public void deleteCompilations(@PathVariable final Long compId) {
        log.info("DELETE /compilations/{compId} <- {}", compId);
        service.deleteCompilation(compId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/admin/compilations/{compId}")
    public Compilations updateCompilations(@PathVariable final Long compId,
                                           @Valid @RequestBody final UpdateCompilationRequest dto) {
        log.info("PATCH /compilations/{compId} <- {}", compId);
        return service.updateCompilation(compId, dto);
    }
}
