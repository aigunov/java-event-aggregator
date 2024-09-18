package ru.practicum.statsdto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CompilationsDto {
    @JsonProperty("events")
    private List<Long> eventIds;
    private Boolean pinned;
    @Size(min = 1, max = 50)
    @NotBlank
    private String title;
    private long id;
    private List<EventDto> events;


}
