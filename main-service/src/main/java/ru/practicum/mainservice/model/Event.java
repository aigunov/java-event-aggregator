package ru.practicum.mainservice.model;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "annotation")
    private String annotation;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @Column(name = "eventDate")
    private LocalDateTime eventDate;

    @Column(name = "publishedOn")
    private LocalDateTime publishedOn;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "participantLimit")
    private int participantLimit;

    @Column(name = "requestModeration")
    private boolean requestModeration;

    @Column(name = "views")
    private int views;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private States state;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "initiator")
    private User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lon")
    private double lon;
}
