package com.aston.thirdTask.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CascadeType;

import java.util.Set;


@Getter
@With
@EqualsAndHashCode(of = "localityId")
@ToString(of = {"localityId", "localityName", "population", "metroAvailability"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locality")
public class LocalityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locality_id")
    private Integer localityId;

    @Column(name = "locality_name")
    private String localityName;

    @Column(name = "population")
    private Integer population;

    @Column(name = "metro_availability")
    private Boolean metroAvailability;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locality")
    private Set<AttractionEntity> attractions;



}
