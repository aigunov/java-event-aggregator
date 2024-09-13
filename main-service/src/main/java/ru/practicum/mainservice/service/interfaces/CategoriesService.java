package ru.practicum.mainservice.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.mainservice.data.dto.CategoryCreate;
import ru.practicum.statsdto.dto.CategoryDto;

import java.util.List;

public interface CategoriesService {
    List<CategoryDto> getCategories(final PageRequest of);

    CategoryDto getCategory(final Long catId);

    CategoryDto createCategory(final CategoryCreate dto);

    void deleteCategory(final Long catId);

    CategoryDto updateCategory(final CategoryDto dto);
}
