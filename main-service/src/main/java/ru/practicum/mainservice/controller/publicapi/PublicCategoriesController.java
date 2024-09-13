package ru.practicum.mainservice.controller.publicapi;


import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.service.interfaces.CategoriesService;
import ru.practicum.statsdto.dto.CategoryDto;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PublicCategoriesController {
    private final CategoriesService service;

    @ResponseStatus(OK)
    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@PositiveOrZero @RequestParam(required = false, defaultValue = "0") final Integer from,
                                           @PositiveOrZero @RequestParam(required = false, defaultValue = "10") final Integer size) {
        log.info("GET /categories");
        return service.getCategories(PageRequest.of(from / size, size));
    }

    @ResponseStatus(OK)
    @GetMapping("/categories/{catId}")
    public CategoryDto getCategory(@PositiveOrZero @PathVariable(required = true) final Long catId) {
        log.info("GET /categories/{}", catId);
        return service.getCategory(catId);
    }
}
