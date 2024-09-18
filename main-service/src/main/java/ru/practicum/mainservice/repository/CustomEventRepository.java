package ru.practicum.mainservice.repository;

import ru.practicum.mainservice.data.dto.EventResponseDto;
import ru.practicum.mainservice.data.model.RequestParameters;

import java.util.List;


public interface CustomEventRepository {

    List<EventResponseDto> adminSearchEvents(final RequestParameters parameters);

    List<EventResponseDto> publicSearchEvents(final RequestParameters parameters);

}
