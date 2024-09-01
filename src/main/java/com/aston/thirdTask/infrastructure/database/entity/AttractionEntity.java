package com.aston.thirdTask.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Getter
@With
@EqualsAndHashCode(of = "attractionId")
@ToString(of = {"attractionId", "attractionName", "creationDate", "description", "type"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attraction")
public class AttractionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id")
    private Integer attractionId;

    @Column(name = "attraction_name")
    private String attractionName;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AttractionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locality_id")
    private LocalityEntity locality;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "attraction_service",
            joinColumns = @JoinColumn(name = "attraction_id"), inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<ServiceEntity> services;

}
