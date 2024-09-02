package ru.practicum.statsdto.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * future
 */
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestDto {
    private int userId;
    private int eventId;

    private int id;
    private LocalDateTime created;
    private int event;
    private int requester;
    private String status;

}
