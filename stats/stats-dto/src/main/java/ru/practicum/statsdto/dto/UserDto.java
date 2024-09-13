package ru.practicum.statsdto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {
    private long id;
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
    @Email
    @NotBlank
    @Size(min = 6, max = 254)
    private String email;
}
