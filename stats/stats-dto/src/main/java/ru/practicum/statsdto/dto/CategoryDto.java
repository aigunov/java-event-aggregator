package ru.practicum.statsdto.dto;

import lombok.*;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CategoryDto {
    private int id;
    private String name;
}
