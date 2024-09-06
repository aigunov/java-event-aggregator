package ru.practicum.mainservice.service.implemetations;

import org.springframework.stereotype.Service;
import ru.practicum.mainservice.service.interfaces.CategoriesService;
import ru.practicum.statsdto.dto.CategoryDto;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Override
    public List<CategoryDto> getCategories() {
        return List.of();
    }

    @Override
    public CategoryDto getCategory(int catId) {
        return null;
    }

    @Override
    public CategoryDto createCategory(CategoryDto dto) {
        return null;
    }

    @Override
    public void deleteCategory(int catId) {

    }

    @Override
    public CategoryDto updateCategory(CategoryDto dto) {
        return null;
    }
}
