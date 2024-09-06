package ru.practicum.mainservice.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.service.interfaces.CategoriesService;
import ru.practicum.statsdto.dto.CategoryDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminCategoriesController {
    private final CategoriesService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/categories")
    public CategoryDto postCategory(@RequestBody final CategoryDto dto) {
        log.info("POST /categories <- {}", dto);
        return service.createCategory(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/categories/{catId}")
    public void deleteCategory(@PathVariable final int catId) {
        log.info("DELETE /categories <- {}", catId);
        service.deleteCategory(catId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/admin/categories/{catId}")
    public CategoryDto patchCategory(@PathVariable final int catId,
                                     @RequestBody final CategoryDto dto) {
        log.info("PATCH /categories/{} <- {}", catId, dto);
        dto.setId(catId);
        return service.updateCategory(dto);
    }

}
