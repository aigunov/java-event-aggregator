package ru.practicum.mainservice.service.implemetations;

import org.springframework.stereotype.Service;
import ru.practicum.mainservice.model.RequestParameters;
import ru.practicum.mainservice.service.interfaces.CompilationsService;
import ru.practicum.statsdto.dto.CompilationsDto;

import java.util.List;

@Service
public class CompilationsServiceImpl implements CompilationsService {
    @Override
    public List<CompilationsDto> getCompilations(RequestParameters body) {
        return List.of();
    }

    @Override
    public CompilationsDto getCompilation(int compId) {
        return null;
    }

    @Override
    public CompilationsDto createCompilation(CompilationsDto dto) {
        return null;
    }

    @Override
    public void deleteCompilation(int compId) {

    }

    @Override
    public CompilationsDto updateCompilation(CompilationsDto dto) {
        return null;
    }
}
