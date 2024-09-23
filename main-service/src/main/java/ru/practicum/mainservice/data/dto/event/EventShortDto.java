package ru.practicum.mainservice.data.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.data.dto.user.UserShortDto;
import ru.practicum.mainservice.data.model.enums.States;
import ru.practicum.statsdto.dto.CategoryDto;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private Long id;
    private UserShortDto initiator;
    private CategoryDto category;
    private LocationDto location;
    private String title;
    private String annotation;
    private String description;
    private Long participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Boolean paid;
    private Boolean requestModeration;
    private States state;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
}