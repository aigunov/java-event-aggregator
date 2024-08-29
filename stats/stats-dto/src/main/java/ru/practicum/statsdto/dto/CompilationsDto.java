package ru.practicum.statsdto.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CompilationsDto {
    @JsonProperty("events")
    private List<Integer> eventIds;
    private boolean pinned;
    private String title;
    private int id;
    private List<EventDto> events;


}
