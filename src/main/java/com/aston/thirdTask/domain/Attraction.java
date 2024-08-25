package com.aston.thirdTask.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "attractionId")
@ToString(of = {"attractionId", "attractionName", "creationDate", "description", "type"})

public class Attraction {
    Integer attractionId;
    String attractionName;
    LocalDate creationDate;
    String description;
    String type;
    Locality locality;
    Set<Service> services;
}