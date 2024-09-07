package ru.practicum.mainservice.model;

import lombok.*;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestParameters {
    private int userId;
    private int[] users;
    private int[] ids;
    private int[] categories;
    private States[] states;
    private LocalDateTime rangeEnd;
    private LocalDateTime rangeStart;
    //    private int size;
//    private int from;
    private String text;
    private boolean paid;
    private boolean onlyAvailable;
    private boolean pinned;
    private Sort sort;
    private PageRequest page;
}
