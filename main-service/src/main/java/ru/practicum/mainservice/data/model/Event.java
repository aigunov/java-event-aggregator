package ru.practicum.mainservice.data.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.mainservice.data.model.enums.States;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "events")
@EqualsAndHashCode(of = {"id"})
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(name = "event_seq", initialValue = 0, allocationSize = 1)
    private long id;

    @Column(name = "annotation")
    private String annotation;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participant_limit")
    private Long participantLimit;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private States state;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "initiator")
    private User initiator;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;
}
