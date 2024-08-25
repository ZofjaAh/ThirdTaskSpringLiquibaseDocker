package com.aston.thirdTask.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Getter
@With
@EqualsAndHashCode(of = "serviceId")
@ToString(of = {"serviceId", "serviceName", "description"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "services")
    private Set<AttractionEntity> attractions;

}
