package ru.practicum.statsserver;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "statistics")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="app")
    private String app;

    @Column(name="uri")
    private String uri;

    @Column(name="ip")
    private String ip;

    @Column(name="timestamp")
    private LocalDateTime timestamp;
}
