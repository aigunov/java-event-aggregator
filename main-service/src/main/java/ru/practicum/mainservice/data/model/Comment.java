package ru.practicum.mainservice.data.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.mainservice.data.model.enums.CommentStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private CommentStatus status;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;
}
