package com.aston.thirdTask.api.dto;

import lombok.*;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionsDTO {
        private List<LocalityDTO.AttractionDTO> attractions;

}
