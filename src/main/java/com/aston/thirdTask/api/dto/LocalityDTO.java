package com.aston.thirdTask.api.dto;

import lombok.*;

import java.util.Set;


@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalityDTO {
    private Integer localityId;
    private String localityName;
    private Integer population;
    private Boolean metroAvailability;
    Set<AttractionDTO> attractions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttractionDTO {
        private Integer attractionId;
        private String attractionName;
        private String creationDate;
        private String description;
        private String type;
        private String serviceNames;
    }
}
