package ru.practicum.mainservice.data.dto.event;

import lombok.Builder;
import lombok.Data;
import ru.practicum.mainservice.data.dto.RequestDto;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class EventRequestStatusUpdateResult {
    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;
}