package ru.practicum.mainservice.service.implemetations;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.data.dto.RequestDto;
import ru.practicum.mainservice.data.dto.event.EventRequestStatus;
import ru.practicum.mainservice.data.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.data.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.data.model.Request;
import ru.practicum.mainservice.data.model.enums.States;
import ru.practicum.mainservice.data.model.enums.Status;
import ru.practicum.mainservice.data.model.exceptions.EntityCreationDataIncorrect;
import ru.practicum.mainservice.data.model.exceptions.EntityUpdateConflict;
import ru.practicum.mainservice.mapper.Mapper;
import ru.practicum.mainservice.repository.EventRepository;
import ru.practicum.mainservice.repository.RequestRepository;
import ru.practicum.mainservice.repository.UserRepository;
import ru.practicum.mainservice.service.interfaces.RequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class RequestServiceImpl implements RequestService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    @Override
    public List<RequestDto> getRequests(final Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        var requests = requestRepository.finUsersRequests(userId).stream().map(Mapper::toRequestDto).toList();
        log.info("Found user's {} requests", requests.size());
        return requests;
    }

    @Override
    public RequestDto createRequest(final Long userId, final Long eventId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        var event = eventRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException("Event not found"));
        if (requestRepository.doesRequestAlreadyExists(userId, eventId) == 1 ||
                event.getInitiator().getId() == userId || !event.getState().equals(States.PUBLISHED) ||
                (requestRepository.getCountParticipants(eventId) >= event.getParticipantLimit() && event.getParticipantLimit() != 0)) {
            throw new EntityCreationDataIncorrect("Нельзя создать подобный запрос");
        }
        var request = Request.builder()
                .created(LocalDateTime.now())
                .requester(user)
                .event(event)
                .status(!event.getRequestModeration() || event.getParticipantLimit() == 0 ? Status.CONFIRMED : Status.PENDING)
                .build();
        request = requestRepository.save(request);
        log.info("Created request {}", request);
        return Mapper.toRequestDto(request);
    }

    @Override
    public RequestDto cancelRequest(final Long userId, final Long requestId) {
        var currentRequest = requestRepository.findAllRequestsByIdAndRequesterId(requestId, userId);
        if (currentRequest.isEmpty()) {
            throw new NoSuchElementException("Пользователь не подавал заявку на участие в событии " +
                    "поэтому нельзя отменить участие в том в чем не участвуешь");
        }
        var request = currentRequest.getFirst();
        request.setStatus(Status.CANCELED);
        requestRepository.save(request);
        return Mapper.toRequestDto(request);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RequestDto> getEventsRequests(final Long userId, final Long eventId) {
        userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        eventRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException("Event not found"));
        var requests = requestRepository.getRequestByEvent(userId, eventId).stream().map(Mapper::toRequestDto).toList();
        log.info("Found user's {} requests", requests.size());
        return requests;
    }

    @Override
    public EventRequestStatusUpdateResult eventCreaterUpdateRequest(final Long userId, final Long eventId, @Valid EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        var event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NoSuchElementException("Пользователь не является инициатором события"));

        if (requestRepository.getCountParticipants(eventId) >= event.getParticipantLimit() &&
                eventRequestStatusUpdateRequest.getStatus().equals(EventRequestStatus.CONFIRMED)) {
            throw new EntityUpdateConflict("Превышен лимит одобренных заявок");
        }

        if (eventRequestStatusUpdateRequest.getStatus() == EventRequestStatus.REJECTED
                && requestRepository.getConfirmedCountListRequestOfEvent(eventRequestStatusUpdateRequest.getRequestIds(), eventId) > 0) {
            throw new EntityUpdateConflict("Попытка отменить уже принятую заявку на участие в событии");
        }

        var requests = requestRepository.getPendingRequestsForEvent(eventRequestStatusUpdateRequest.getRequestIds(), eventId);

        if (eventRequestStatusUpdateRequest.getStatus() == EventRequestStatus.REJECTED) {
            requests.forEach(r -> r.setStatus(Status.REJECTED));
            requestRepository.saveAll(requests);
            return EventRequestStatusUpdateResult.builder()
                    .confirmedRequests(List.of())
                    .rejectedRequests(requests.stream().map(Mapper::toRequestDto).toList())
                    .build();
        }
        int confirmedCount = 0;
        Long participantLimit = event.getParticipantLimit();
        Long confirmedParticipants = requestRepository.getCountParticipants(eventId);

        for (Request req : requests) {
            if (confirmedParticipants >= participantLimit && participantLimit > 0) {
                break;
            }
            req.setStatus(Status.CONFIRMED);
            confirmedParticipants++;
            confirmedCount++;
        }

        List<Request> confirmedRequests = requests.subList(0, confirmedCount);
        List<Request> rejectedRequests = requests.subList(confirmedCount, requests.size());

        rejectedRequests.forEach(r -> r.setStatus(Status.REJECTED));
        requestRepository.saveAll(requests);

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests.stream().map(Mapper::toRequestDto).toList())
                .rejectedRequests(rejectedRequests.stream().map(Mapper::toRequestDto).toList())
                .build();
    }
}
