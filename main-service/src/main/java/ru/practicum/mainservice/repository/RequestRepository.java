package ru.practicum.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.data.model.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>, CustomRequestRepository {

    @Query(value = """
            select * from requests
            where requester_id = :requesterId
            """, nativeQuery = true)
    List<Request> finUsersRequests(@Param("requesterId") final Long id);

    @Query(value = """
            select count(*) from requests
            where requester_id = :requesterId
            and event_id = :eventId
            """, nativeQuery = true)
    int doesRequestAlreadyExists(@Param("requesterId") final long userId,
                                 @Param("eventId") final long eventId);


    List<Request> findAllRequestsByIdAndRequesterId(final Long id, final Long requesterId);
}