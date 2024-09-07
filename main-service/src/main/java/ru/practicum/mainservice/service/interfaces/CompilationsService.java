package ru.practicum.mainservice.service.interfaces;

import ru.practicum.mainservice.model.RequestParameters;
import ru.practicum.statsdto.dto.CompilationsDto;

import java.util.List;

public interface CompilationsService {
    List<CompilationsDto> getCompilations(final RequestParameters body);

    CompilationsDto getCompilation(int compId);

    CompilationsDto createCompilation(final CompilationsDto dto);

    void deleteCompilation(final int compId);

    CompilationsDto updateCompilation(final CompilationsDto dto);
}
