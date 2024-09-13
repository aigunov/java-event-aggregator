package ru.practicum.mainservice.service.implemetations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.data.dto.CategoryCreate;
import ru.practicum.mainservice.data.model.Category;
import ru.practicum.mainservice.data.model.EntityUpdateConflict;
import ru.practicum.mainservice.mapper.Mapper;
import ru.practicum.mainservice.repository.CategoriesRepository;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.service.interfaces.CategoriesService;
import ru.practicum.statsdto.dto.CategoryDto;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class CategoriesServiceImpl implements CategoriesService {
    private final EventRepository eventRepository;
    private final CategoriesRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> getCategories(final PageRequest of) {
        List<CategoryDto> dtos = repository.findAll(of).stream().map(Mapper::toCategoryDto).toList();
        log.info("getCategories: {}", dtos);
        return dtos;
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDto getCategory(final Long catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));
        log.info("getCategory: {}", category);
        return Mapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto createCategory(final CategoryCreate dto) {
        var category = repository.save(Category.builder().name(dto.getName()).build());
        log.info("createCategory: {}", category);
        var cat = Mapper.toCategoryDto(category);
        return cat;
    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = repository.findById(catId).orElseThrow(NoSuchElementException::new);
        if (eventRepository.countByCategory(category) > 0) {
            throw new EntityUpdateConflict("The category is not empty");
        }
        repository.deleteById(catId);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto dto) {
        Category cat = repository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
        cat.setName(dto.getName() == null ? cat.getName() : dto.getName());
        log.info("updateCategory: {}", cat);
        return Mapper.toCategoryDto(repository.save(cat));
    }
}
