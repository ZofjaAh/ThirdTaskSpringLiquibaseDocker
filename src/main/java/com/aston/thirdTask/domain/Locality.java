package com.aston.thirdTask.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "localityId")
@ToString(of = {"localityId", "localityName", "population", "metroAvailability"})

public class Locality {
    Integer localityId;
    String localityName;
    Integer population;
    Boolean metroAvailability;
    Set<Attraction> attractions;
}