package ru.practicum.mainservice.controller.publicapi;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainservice.service.interfaces.CategoriesService;
import ru.practicum.statsdto.dto.CategoryDto;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController("/categories")
@RequiredArgsConstructor
public class PublicCategoriesController {
    private final CategoriesService service;

    @ResponseStatus(OK)
    @GetMapping
    public List<CategoryDto> getCategories() {
        log.info("GET /categories");
        return service.getCategories();
    }

    @ResponseStatus(OK)
    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable final int catId) {
        log.info("GET /categories/{}", catId);
        return service.getCategory(catId);
    }
}
