package ru.practicum.mainservice.data.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.data.model.States;
import ru.practicum.statsdto.dto.CategoryDto;
import ru.practicum.statsdto.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {
    private Long id;
    private UserDto initiator;
    private CategoryDto category;
    private LocationDto location;
    private String title;
    private String annotation;
    private String description;
    private Long participantLimit;
    private Long confirmedRequests;
    private Boolean paid;

    private Boolean requestModeration;
    private States state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private Long views;
}