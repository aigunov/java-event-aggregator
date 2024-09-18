package ru.practicum.mainservice.data.model;

import lombok.*;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestParameters {
    private long userId;
    private List<Long> users;
    private List<Long> ids;
    private List<Long> categories;
    private List<States> states;
    private LocalDateTime rangeEnd;
    private LocalDateTime rangeStart;
    private String text;
    private Boolean paid;
    private Boolean onlyAvailable;
    private Boolean pinned;
    private Sort sort;
    private PageRequest page;

    public void checkValid() {
        if (rangeStart != null && rangeEnd != null) {
            if (rangeStart.isAfter(rangeEnd)) {
                throw new NoValidParameter("Невалидные данные");
            }
        }
    }
}
