package com.aston.thirdTask.infrastructure.database.repository;

import com.aston.thirdTask.buisness.dao.AttractionDAO;
import com.aston.thirdTask.domain.Attraction;
import com.aston.thirdTask.domain.exception.NotFoundException;
import com.aston.thirdTask.infrastructure.database.entity.AttractionEntity;
import com.aston.thirdTask.infrastructure.database.entity.AttractionType;
import com.aston.thirdTask.infrastructure.database.mapper.AttractionEntityMapper;
import com.aston.thirdTask.infrastructure.database.repository.jpa.AttractionJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Repository class for managing Attraction entities.
 */
@Repository
@AllArgsConstructor
@Slf4j
public class AttractionRepository implements AttractionDAO {
    private final AttractionJpaRepository attractionJpaRepository;
    private final AttractionEntityMapper attractionEntityMapper;

    /**
     * Creates a new attraction.
     *
     * @param attraction the attraction to be created.
     * @return the created attraction.
     */
    @Override
    public Attraction create(Attraction attraction) {
        AttractionEntity entity = attractionEntityMapper.map(attraction);
        log.info("Attraction entity created: {}", entity);
        return attractionEntityMapper.mapFromEntity(
                attractionJpaRepository.saveAndFlush(
                        entity));
    }

    /**
     * Finds all attractions by locality name.
     *
     * @param localityName the name of the locality.
     * @return a set of attractions in the specified locality.
     */
    @Override
    public Set<Attraction> findAllByLocalityName(String localityName) {
        return attractionJpaRepository.findByLocalityName(localityName).stream()
                .map(attractionEntityMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    /**
     * Finds all attractions with sorting and filtering.
     *
     * @param sortingDirection the direction of sorting (e.g., "asc" or "desc").
     * @param typeFilter       the type filter for attractions.
     * @return a list of attractions that match the sorting and filtering criteria.
     */
    @Override
    public List<Attraction> findAllWithSortingAndFilter(String sortingDirection, String typeFilter) {
        Boolean isExistType = typeAttractionIsExist(typeFilter);
        List<AttractionEntity> attractionEntities = new ArrayList<>();
        if (sortRecognise(sortingDirection)) {
            Sort.Direction direction = Sort.Direction.fromString(sortingDirection);
            Sort sort = Sort.by(direction, "attractionName");
            if (isExistType) {
                AttractionType type = AttractionType.valueOf(typeFilter.toUpperCase());
                attractionEntities = attractionJpaRepository.findAllWithSortAndFilter(type, sort);
            } else {
                attractionEntities = attractionJpaRepository.findAllWithSorting(sort);
            }
        } else if (isExistType) {
            AttractionType type = AttractionType.valueOf(typeFilter.toUpperCase());
            attractionEntities = attractionJpaRepository.findAllWithFilter(type);
        } else {
            attractionEntities = attractionJpaRepository.findAll();
        }
        return attractionEntities.stream()
                .map(attractionEntityMapper::mapFromEntity)
                .toList();
    }

    /**
     * Changes the description of an attraction.
     *
     * @param attractionId the ID of the attraction.
     * @param description  the new description.
     * @return the number of records updated.
     * @throws NotFoundException if the attraction is not found.
     */
    @Override
    public int changeAttractionDescription(Integer attractionId, String description) {
        if (attractionJpaRepository.findById(attractionId).isPresent()) {
            log.info("Attraction with Id: [{}] exists", attractionId);
            return attractionJpaRepository.changeAttractionDescriptionById(attractionId, description);
        } else {
            log.error("Attraction with Id: [{}] does not exist", attractionId);
            throw new NotFoundException(String.format("Attraction with Id: %s does not exist", attractionId));
        }
    }

    /**
     * Deletes an attraction.
     *
     * @param attractionId the ID of the attraction to be deleted.
     * @throws NotFoundException if the attraction is not found.
     */
    @Transactional
    @Override
    public void deleteAttraction(Integer attractionId) {
        Optional<AttractionEntity> attractionEntityOptional = attractionJpaRepository.findById(attractionId);
        if (attractionEntityOptional.isPresent()) {
            log.info("Attraction with Id: [{}] exists", attractionId);
            AttractionEntity attractionEntity = attractionEntityOptional.get();
            attractionEntity.getServices().clear();
            attractionJpaRepository.save(attractionEntity);

            attractionJpaRepository.deleteById(attractionId);
            log.info("Removing attraction with Id: [{}] was successful", attractionId);
        } else {
            log.error("Couldn't delete attraction with id: [{}] - It does not exist", attractionId);
            throw new NotFoundException(String.format("Couldn't delete attraction with id: %s - It does not exist", attractionId));
        }
    }

    /**
     * Checks if the attraction type exists.
     *
     * @param typeFilter the type filter for attractions.
     * @return true if the type exists, false otherwise.
     */
    private Boolean typeAttractionIsExist(String typeFilter) {
        if (Objects.isNull(typeFilter)) {
            log.error("Attraction Type specified for filtering - Is Null");
            return false;
        } else {
            try {
                AttractionType.valueOf(typeFilter.toUpperCase());
                log.info("Attraction Type : [{}] specified for filtering - Exists", typeFilter);
                return true;
            } catch (IllegalArgumentException e) {
                log.error("Attraction Type : [{}] specified for filtering - Does Not Exist", typeFilter);
                return false;
            }
        }
    }

    /**
     * Recognizes the sorting direction.
     *
     * @param sortingDirection the direction of sorting.
     * @return true if the sorting direction is valid, false otherwise.
     */
    private boolean sortRecognise(String sortingDirection) {
        if (Objects.isNull(sortingDirection)) {
            log.error("SortingDirection specified for sorting - Is Null");
            return false;
        } else {
            List<String> sortTypeList = List.of("DESC", "ASC");
            if (sortTypeList.contains(sortingDirection)) {
                log.info("Sorting Type : [{}] specified for Attraction Name - Exists", sortingDirection);
                return true;
            } else
                log.error("Sorting Type : [{}] specified for Attraction Name - Does Not Exist", sortingDirection);
            return false;

        }
    }

}

