package com.aston.thirdTask.api.dto.mapper;


import com.aston.thirdTask.api.dto.LocalityDTO;
import com.aston.thirdTask.domain.Locality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocalityMapper extends LocalDateMapper {

    @Mapping(target = "attractions", ignore = true)
    LocalityDTO map(Locality locality);

    default Locality map(LocalityDTO localityDTO) {
        return Locality.builder()
                .localityName(localityDTO.getLocalityName())
                .population(localityDTO.getPopulation())
                .metroAvailability(localityDTO.getMetroAvailability())
                .build();
    }
}
