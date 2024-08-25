package com.aston.thirdTask.api.dto;

import lombok.*;

import java.util.Set;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionServiceDTO {
        private Integer attractionId;
        private String attractionName;
        private String creationDate;
        private String description;
        private String type;
        private Integer locality;
    Set<ServiceDTO> services;
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceDTO {
        private String serviceName;
        private String  description;
    }
}
