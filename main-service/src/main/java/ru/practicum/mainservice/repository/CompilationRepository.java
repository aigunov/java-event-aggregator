package ru.practicum.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.model.Compilations;

public interface CompilationRepository extends JpaRepository<Compilations, Long> {
}
