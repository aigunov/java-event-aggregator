package ru.practicum.statsdto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EventDto {
    //---OnCreateFields---
    @Size(min = 20, max = 2000)
    private String annotation;
    private long categoryId;
    private long userId;
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @Valid
    private Location location;
    private boolean paid;
    @PositiveOrZero
    private long participantLimit;
    private boolean requestModeration;
    @Size(min = 3, max = 120)
    private String title;

    //---OnResponseFields---
    private long id;
    private CategoryDto category;
    private long confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private String state;
    private long views;
    private UserDto initiator;
    private String stateAction;

    @Builder
    public record Location(
            @NotNull
            @Min(-90)
            @Max(90)
            double lat,
            @NotNull
            @Min(-180)
            @Max(180)
            double lon) {
    }
}
