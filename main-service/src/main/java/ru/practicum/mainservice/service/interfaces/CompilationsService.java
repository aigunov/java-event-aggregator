package ru.practicum.mainservice.service.interfaces;

import ru.practicum.mainservice.model.RequestParametersDTO;
import ru.practicum.statsdto.dto.CompilationsDto;

import java.util.List;

public interface CompilationsService {
    List<CompilationsDto> getCompilations(final RequestParametersDTO body);

    CompilationsDto getCompilation(int compId);

    CompilationsDto createCompilation(final CompilationsDto dto);

    void deleteCompilation(final int compId);

    CompilationsDto updateCompilation(final CompilationsDto dto);
}
