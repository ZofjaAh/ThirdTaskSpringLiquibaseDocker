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

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface AttractionEntityMapper {

    @Mapping(target = "locality.attractions", ignore = true)
    @Mapping(source = "services", target = "services", qualifiedByName = "mapServices")
    @Mapping(target = "type", source = "type", qualifiedByName = "enumToString")
    Attraction mapFromEntity(AttractionEntity attractionEntity);

    @Mapping(target = "type", source = "type", qualifiedByName = "stringToEnum")
    AttractionEntity map(Attraction attraction);

    @Named("stringToEnum")
    default AttractionType stringToEnum(String type) {
        return AttractionType.valueOf(type.toUpperCase());
    }
    @Named("enumToString")
    default String enumToString(AttractionType type) {
        return type.name().toLowerCase();
    }

    @Named("mapServices")
    default Set<Service> mapServices(Set<ServiceEntity> serviceEntities) {
        if( Objects.isNull(serviceEntities)) {
            return null;
        }else return serviceEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }
    @Mapping(target = "attractions", ignore = true)
    Service mapFromEntity(ServiceEntity entity);
}
