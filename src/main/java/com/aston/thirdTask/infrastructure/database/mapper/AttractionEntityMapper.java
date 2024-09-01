package com.aston.thirdTask.infrastructure.database.mapper;

import com.aston.thirdTask.domain.Attraction;
import com.aston.thirdTask.domain.Service;
import com.aston.thirdTask.infrastructure.database.entity.AttractionEntity;
import com.aston.thirdTask.infrastructure.database.entity.AttractionType;
import com.aston.thirdTask.infrastructure.database.entity.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper interface for converting between Attraction and AttractionEntity.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttractionEntityMapper {
    /**
     * Maps an AttractionEntity to an Attraction.
     *
     * @param attractionEntity the entity to map from.
     * @return the mapped Attraction.
     */
    @Mapping(target = "locality.attractions", ignore = true)
    @Mapping(source = "services", target = "services", qualifiedByName = "mapServices")
    @Mapping(target = "type", source = "type", qualifiedByName = "enumToString")
    Attraction mapFromEntity(AttractionEntity attractionEntity);

    /**
     * Maps an Attraction to an AttractionEntity.
     *
     * @param attraction the attraction to map from.
     * @return the mapped AttractionEntity.
     */
    @Mapping(target = "type", source = "type", qualifiedByName = "stringToEnum")
    AttractionEntity map(Attraction attraction);

    /**
     * Converts a string to an AttractionType enum.
     *
     * @param type the string to convert.
     * @return the corresponding AttractionType.
     */
    @Named("stringToEnum")
    default AttractionType stringToEnum(String type) {
        return AttractionType.valueOf(type.toUpperCase());
    }

    /**
     * Converts an AttractionType enum to a string.
     *
     * @param type the enum to convert.
     * @return the corresponding string.
     */
    @Named("enumToString")
    default String enumToString(AttractionType type) {
        return type.name().toLowerCase();
    }

    /**
     * Maps a set of ServiceEntity to a set of Service.
     *
     * @param serviceEntities the set of ServiceEntity to map from.
     * @return the mapped set of Service.
     */
    @Named("mapServices")
    default Set<Service> mapServices(Set<ServiceEntity> serviceEntities) {
        if (Objects.isNull(serviceEntities)) {
            return null;
        } else return serviceEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    /**
     * Maps a ServiceEntity to a Service.
     *
     * @param entity the entity to map from.
     * @return the mapped Service.
     */
    @Mapping(target = "attractions", ignore = true)
    Service mapFromEntity(ServiceEntity entity);
}
