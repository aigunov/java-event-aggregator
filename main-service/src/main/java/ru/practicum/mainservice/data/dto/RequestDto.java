package ru.practicum.mainservice.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.mainservice.data.model.Status;

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
    private long userId;
    private long eventId;

    private long id;
    private long requester;
    private long event;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private Status status;
}
