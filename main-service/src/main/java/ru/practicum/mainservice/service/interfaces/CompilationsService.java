package ru.practicum.mainservice.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.mainservice.data.dto.compilation.NewCompilationDto;
import ru.practicum.mainservice.data.dto.compilation.UpdateCompilationRequest;
import ru.practicum.mainservice.data.model.Compilations;

import java.util.List;

public interface CompilationsService {
    List<Compilations> getCompilations(final Boolean pinned, final PageRequest pageRequest);

    Compilations getCompilation(Long compId);

    Compilations createCompilation(final NewCompilationDto dto);

    void deleteCompilation(final Long compId);

    Compilations updateCompilation(final Long compId, final UpdateCompilationRequest dto);
}
