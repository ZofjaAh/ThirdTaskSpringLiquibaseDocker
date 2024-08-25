package com.aston.thirdTask.infrastructure.database.mapper;

import com.aston.thirdTask.domain.Attraction;
import com.aston.thirdTask.domain.Locality;
import com.aston.thirdTask.infrastructure.database.entity.AttractionEntity;
import com.aston.thirdTask.infrastructure.database.entity.LocalityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface LocalityEntityMapper {

    @Mapping(source = "attractions", target = "attractions", qualifiedByName = "mapAttractions")

    Locality mapFromEntity(LocalityEntity localityEntity);

    LocalityEntity map(Locality locality);
    @Named("mapAttractions")
    default Set<Attraction> mapAttractions(Set<AttractionEntity> attractionEntities) {
        if( Objects.isNull(attractionEntities)) {
            return null;
        }else return attractionEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }
    @Mapping(target = "locality", ignore = true)
    @Mapping(target = "services", ignore = true)
    Attraction mapFromEntity(AttractionEntity entity);
}

