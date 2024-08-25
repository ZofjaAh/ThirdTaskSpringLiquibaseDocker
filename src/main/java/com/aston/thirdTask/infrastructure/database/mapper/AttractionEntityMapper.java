package com.aston.thirdTask.infrastructure.database.mapper;

import com.aston.thirdTask.domain.Attraction;
import com.aston.thirdTask.domain.Service;
import com.aston.thirdTask.infrastructure.database.entity.AttractionEntity;
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
    Attraction mapFromEntity(AttractionEntity attractionEntity);

    AttractionEntity map(Attraction attraction);
    @Named("mapServices")
    default Set<Service> mapServices(Set<ServiceEntity> serviceEntities) {
        if( Objects.isNull(serviceEntities)) {
            return null;
        }else return serviceEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }
    @Mapping(target = "attractions", ignore = true)
    Service mapFromEntity(ServiceEntity entity);
}
