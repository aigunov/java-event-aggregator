package ru.practicum.mainservice.service.interfaces;

import ru.practicum.statsdto.dto.CategoryDto;

import java.util.List;

public interface CategoriesService {
    List<CategoryDto> getCategories();

    CategoryDto getCategory(final int catId);

    CategoryDto createCategory(final CategoryDto dto);

    void deleteCategory(final int catId);

    CategoryDto updateCategory(final CategoryDto dto);
}
