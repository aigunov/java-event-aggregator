package ru.practicum.mainservice.service.implemetations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.data.dto.compilation.NewCompilationDto;
import ru.practicum.mainservice.data.dto.compilation.UpdateCompilationRequest;
import ru.practicum.mainservice.data.model.Compilations;
import ru.practicum.mainservice.data.model.Event;
import ru.practicum.mainservice.mapper.Mapper;
import ru.practicum.mainservice.repository.CompilationRepository;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.service.interfaces.CompilationsService;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class CompilationsServiceImpl implements CompilationsService {
    private final EventRepository eventRepository;
    private final CompilationRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Compilations> getCompilations(final Boolean pinned, final PageRequest of) {
        var list = pinned == null ? repository.findAll(of).stream().toList()
                : repository.findAllCompilationsByPinned(pinned, of);
        log.info("Compilation list: {}", list);
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public Compilations getCompilation(Long compId) {
        return repository.findById(compId)
                .orElseThrow(() -> new NoSuchElementException("Compilation with id=" + compId + " was not found"));
    }

    @Override
    public Compilations createCompilation(final NewCompilationDto dto) {
        var events = dto.getEvents() == null ? new HashSet<Event>() :
                new HashSet<>(eventRepository.findAllById(dto.getEvents()));
        var compilation = Mapper.toCompilations(dto, events);
        log.info("Created compilation: {}", compilation);
        return repository.save(compilation);
    }

    @Override
    public void deleteCompilation(Long compId) {
        var compilation = repository.findById(compId)
                .orElseThrow(() -> new IllegalArgumentException("Compilation with id=" + compId + " was not found"));
        log.info("Deleted compilation: {}", compilation);
        repository.delete(compilation);
    }

    @Override
    public Compilations updateCompilation(final Long compId,
                                          final UpdateCompilationRequest dto) {
        var compilation = repository.findById(compId)
                .orElseThrow(() -> new IllegalArgumentException("Compilation with id=" + compId + " was not found"));
        var events = dto.getEvents() == null ? new HashSet<Event>() :
                new HashSet<>(eventRepository.findAllById(dto.getEvents()));
        compilation = Mapper.toCompilationUpdate(compilation, events, dto);
        log.info("Updated compilation: {}", compilation);
        repository.save(compilation);
        return compilation;
    }
}

