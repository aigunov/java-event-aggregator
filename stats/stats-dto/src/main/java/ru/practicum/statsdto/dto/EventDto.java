package ru.practicum.statsdto.dto;

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
    private String annotation;
    private int categoryId;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;

    //---OnResponseFields---
    private int id;
    private CategoryDto category;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private String state;
    private int views;
    private UserDto initiator;
    private String stateAction;


    @Builder
    public record Location(double lat, double lon) {
    }
}
