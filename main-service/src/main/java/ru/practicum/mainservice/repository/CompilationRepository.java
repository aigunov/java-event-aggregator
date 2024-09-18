package ru.practicum.mainservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.data.model.Compilations;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilations, Long> {

    List<Compilations> findAllCompilationsByPinned(final boolean pinned, final Pageable pageable);
}
