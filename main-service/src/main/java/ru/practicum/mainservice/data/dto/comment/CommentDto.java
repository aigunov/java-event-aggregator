package ru.practicum.mainservice.data.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ru.practicum.mainservice.data.model.enums.CommentStatus;
import ru.practicum.statsdto.dto.UserDto;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommentDto {
    private Long id;
    private Long eventId;
    private String comment;
    private UserDto user;
    @NotBlank
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private CommentStatus status;
}
