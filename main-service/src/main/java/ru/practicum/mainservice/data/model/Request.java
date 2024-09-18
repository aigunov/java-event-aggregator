package ru.practicum.mainservice.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created")
    private LocalDateTime created;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id")
    private User requester;
}
