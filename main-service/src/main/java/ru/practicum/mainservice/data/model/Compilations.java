package ru.practicum.mainservice.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "compilations")
public class Compilations {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compilation_seq")
    @SequenceGenerator(name = "compilation_seq", initialValue = 0, allocationSize = 1)
    private long id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events;


    @Column(name = "pinned")
    private boolean pinned;
}
