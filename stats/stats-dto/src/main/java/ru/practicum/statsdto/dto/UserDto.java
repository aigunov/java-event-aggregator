package ru.practicum.statsdto.dto;

import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {
    private int id;
    private String name;
    private String email;
}
