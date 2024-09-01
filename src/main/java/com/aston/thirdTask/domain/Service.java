package com.aston.thirdTask.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "serviceId")
@ToString(of = {"serviceId", "serviceName", "description"})

public class Service {
    Integer serviceId;
    String serviceName;
    String description;
    Set<Attraction> attractions;

}