package ru.practicum.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.data.model.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
}
