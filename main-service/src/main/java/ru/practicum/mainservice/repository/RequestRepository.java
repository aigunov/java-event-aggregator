package ru.practicum.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
