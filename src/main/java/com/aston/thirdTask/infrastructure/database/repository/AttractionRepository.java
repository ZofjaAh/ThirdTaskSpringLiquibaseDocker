package com.aston.thirdTask.infrastructure.database.repository;

import com.aston.thirdTask.buisness.dao.AttractionDAO;
import com.aston.thirdTask.domain.Attraction;
import com.aston.thirdTask.domain.exception.NotFoundException;
import com.aston.thirdTask.infrastructure.database.entity.AttractionEntity;
import com.aston.thirdTask.infrastructure.database.entity.AttractionType;
import com.aston.thirdTask.infrastructure.database.mapper.AttractionEntityMapper;
import com.aston.thirdTask.infrastructure.database.repository.jpa.AttractionJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@Slf4j

public class AttractionRepository implements AttractionDAO {
    private final AttractionJpaRepository attractionJpaRepository;
    private final AttractionEntityMapper attractionEntityMapper;

    @Override
    public Attraction create(Attraction attraction) {
        return attractionEntityMapper.mapFromEntity(
                attractionJpaRepository.saveAndFlush(
                        attractionEntityMapper.map(attraction)));
    }

    @Override
    public Set<Attraction> findAllByLocalityName(String localityName) {
        return attractionJpaRepository.findByLocalityName(localityName).stream()
                .map(attractionEntityMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Attraction> findAllWithSortingAndFilter(String sortingDirection, String typeFilter) {
        Boolean isExistType = typeAttractionIsExist(typeFilter);
        List<AttractionEntity> attractionEntities  = new ArrayList<>();
        if (sortRecognise(sortingDirection)) {
            Sort.Direction direction = Sort.Direction.fromString(sortingDirection);
            Sort sort = Sort.by(direction, "attractionName");
            if (isExistType) {
                attractionEntities = attractionJpaRepository.findAllWithSortAndFilter(typeFilter, sort);
            }   else {
                attractionEntities = attractionJpaRepository.findAllWithSorting(sort);
        }
        }else if (isExistType) {
            attractionEntities =  attractionJpaRepository.findAllWithFilter(typeFilter);
        } else {attractionEntities = attractionJpaRepository.findAll();}
       return attractionEntities.stream()
                .map(attractionEntityMapper::mapFromEntity)
                .toList();
    }


    @Override
    public Attraction changeAttractionDescription(Integer attractionId, String description) {
       if(attractionJpaRepository.findById(attractionId).isPresent()) {
           log.info("Attraction with Id: [{}] exists", attractionId);
           return attractionJpaRepository.changeAttractionDescriptionById(attractionId, description);
       }
       log.error("Attraction with Id: [{}] does not exist", attractionId);
       throw new NotFoundException(String.format("Attraction with Id: %s does not exist", attractionId));
    }
    @Override
    public void deleteAttraction(Integer attractionId){
        if(attractionJpaRepository.findById(attractionId).isPresent()) {
            log.info("Attraction with Id: [{}] exists", attractionId);
             attractionJpaRepository.deleteById(attractionId);
             log.info("Removing attraction with Id: [{}] was successful", attractionId);
        }
        log.error("Couldn't delete attraction with id: [{}] - It does not exist", attractionId);
        throw new NotFoundException(String.format("Couldn't delete attraction with id: %s - It does not exist", attractionId));
    }


    private Boolean typeAttractionIsExist(String typeFilter) {
        try {
            AttractionType.valueOf(typeFilter.toUpperCase());
            log.info("Attraction Type : [{}] specified for filtering - Exists", typeFilter);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("Attraction Type : [{}] specified for filtering - Does Not Exist", typeFilter);
            return false;
        }
    }

    private boolean sortRecognise(String sortingDirection) {
        List<String> sortTypeList = List.of("DESC", "ASC");
        if(sortTypeList.contains(sortingDirection)){
            log.info("Sorting Type : [{}] specified for Attraction Name - Exists", sortingDirection);
            return true;
        } else
            log.error("Sorting Type : [{}] specified for Attraction Name - Does Not Exist", sortingDirection);
        return false;

    }

}

