package ru.practicum.mainservice.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.data.dto.comment.CommentDto;
import ru.practicum.mainservice.data.model.Comment;
import ru.practicum.mainservice.data.model.enums.CommentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = """
            select * 
            from comments as c
            where c.event_id = :eventId
            and c.id in (:ids) 
            """, nativeQuery = true)
    List<Comment> getCommentsByIdsAndEventId(@Param("ids") final Set<Long> ids,
                                             @Param("eventId") final Long eventId);

    @Transactional
    @Modifying
    @Query("UPDATE Comment c SET c.status = CommentStatus.APPROVED, c.publishedOn = ?1 WHERE c.id IN(?2)")
    void publishComment(LocalDateTime published, List<Long> ids);

    @Transactional
    @Modifying
    @Query("UPDATE Comment c SET c.status =  CommentStatus.REJECTED WHERE c.id IN(:ids)")
    void rejectComment(@Param("ids") final List<Long> ids);


    List<Comment> getCommentsByEventId(Long eventId, PageRequest of);

    Optional<Comment> findByEventIdAndId(Long eventId, Long commentId);

}
