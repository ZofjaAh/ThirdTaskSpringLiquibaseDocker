package com.aston.thirdTask.api.dto;

import lombok.*;


@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDTO {
        private Integer attractionId;
        private String attractionName;
        private String creationDate;
        private String description;
        private String type;
        private String localityName;

}
