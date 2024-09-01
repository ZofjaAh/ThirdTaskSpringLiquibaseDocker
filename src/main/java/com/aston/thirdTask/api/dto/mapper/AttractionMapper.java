package com.aston.thirdTask.api.dto.mapper;


import com.aston.thirdTask.api.dto.AttractionDTO;
import com.aston.thirdTask.api.dto.LocalityDTO;
import com.aston.thirdTask.domain.Attraction;
import com.aston.thirdTask.domain.Service;
import org.mapstruct.Mapper;

import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AttractionMapper extends LocalDateMapper {

    default Attraction map(AttractionDTO attractionDTO) {
        return Attraction.builder()
                .attractionName(attractionDTO.getAttractionName())
                .creationDate(mapStringToLocalDate(attractionDTO.getCreationDate()))
                .description(attractionDTO.getDescription())
                .type(attractionDTO.getType())
                .build();
    }

    default AttractionDTO map(Attraction attraction) {
        return AttractionDTO.builder()
                .attractionId(attraction.getAttractionId())
                .attractionName(attraction.getAttractionName())
                .creationDate(mapLocalDateToString(attraction.getCreationDate()))
                .description(attraction.getDescription())
                .type(attraction.getType())
                .localityName(attraction.getLocality().getLocalityName())
                .build();

    }


    default LocalityDTO.AttractionDTO mapToAttractionWithService(Attraction attraction) {
        return LocalityDTO.AttractionDTO.builder()
                .attractionId(attraction.getAttractionId())
                .attractionName(attraction.getAttractionName())
                .creationDate(mapLocalDateToString(attraction.getCreationDate()))
                .description(attraction.getDescription())
                .type(attraction.getType())
                .serviceNames(Objects.nonNull(attraction.getServices()) ?
                        attraction.getServices().stream()
                                .map(Service::getServiceName)
                                .collect(Collectors.joining(", ")) : null)
                .build();
    }


}
