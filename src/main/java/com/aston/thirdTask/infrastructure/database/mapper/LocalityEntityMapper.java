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

/**
 * Mapper interface for converting between Locality and LocalityEntity.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalityEntityMapper {
    /**
     * Maps a LocalityEntity to a Locality.
     *
     * @param localityEntity the entity to map from.
     * @return the mapped Locality.
     */
    @Mapping(source = "attractions", target = "attractions", qualifiedByName = "mapAttractions")
    Locality mapFromEntity(LocalityEntity localityEntity);

    /**
     * Maps a Locality to a LocalityEntity.
     *
     * @param locality the locality to map from.
     * @return the mapped LocalityEntity.
     */
    LocalityEntity map(Locality locality);

    /**
     * Maps a set of AttractionEntity to a set of Attraction.
     *
     * @param attractionEntities the set of AttractionEntity to map from.
     * @return the mapped set of Attraction.
     */
    @Named("mapAttractions")
    default Set<Attraction> mapAttractions(Set<AttractionEntity> attractionEntities) {
        if (Objects.isNull(attractionEntities)) {
            return null;
        } else return attractionEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    /**
     * Maps an AttractionEntity to an Attraction.
     *
     * @param entity the entity to map from.
     * @return the mapped Attraction.
     */
    @Mapping(target = "locality", ignore = true)
    @Mapping(target = "services", ignore = true)
    Attraction mapFromEntity(AttractionEntity entity);
}

