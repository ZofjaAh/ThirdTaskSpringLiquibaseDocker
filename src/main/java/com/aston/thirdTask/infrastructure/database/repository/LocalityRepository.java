package com.aston.thirdTask.infrastructure.database.repository;

import com.aston.thirdTask.buisness.dao.LocalityDAO;
import com.aston.thirdTask.domain.Locality;
import com.aston.thirdTask.domain.exception.NotFoundException;
import com.aston.thirdTask.domain.exception.ProcessingException;
import com.aston.thirdTask.infrastructure.database.mapper.LocalityEntityMapper;
import com.aston.thirdTask.infrastructure.database.repository.jpa.LocalityJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * Repository class for managing Locality entities.
 */
@Slf4j
@Repository
@AllArgsConstructor
public class LocalityRepository implements LocalityDAO {
    private final LocalityJpaRepository localityJpaRepository;
    private final LocalityEntityMapper localityEntityMapper;

    /**
     * Creates a new locality.
     *
     * @param locality the locality to be created.
     * @return the created locality.
     */
    @Override
    public Locality create(Locality locality) {
        return localityEntityMapper.mapFromEntity(
                localityJpaRepository.saveAndFlush(
                        localityEntityMapper.map(locality)));
    }

    /**
     * Finds a locality by its name.
     *
     * @param localityName the name of the locality.
     * @return an Optional containing the found locality, or an empty Optional if not found.
     */
    @Override
    public Optional<Locality> findLocalityByName(String localityName) {
        return localityJpaRepository.findByLocalityName(localityName)
                .map(localityEntityMapper::mapFromEntity);

    }

    /**
     * Changes the population and/or metro availability of a locality.
     *
     * @param localityId        the ID of the locality.
     * @param population        the new population (optional).
     * @param metroAvailability the new metro availability status (optional).
     * @return the number of records updated.
     * @throws NotFoundException   if the locality is not found.
     * @throws ProcessingException if both population and metro availability are not specified.
     */
    @Override
    public int changeLocality(Integer localityId, Integer population, Boolean metroAvailability) {
        if (localityJpaRepository.findById(localityId).isPresent()) {
            log.info("Locality with Id: [{}] exists", localityId);
            if (Objects.nonNull(population)) {
                log.info("Parameter attraction population is specified: [{}]", population);
                if (Objects.nonNull(metroAvailability)) {
                    log.info("Parameter attraction metro availability is specified: [{}]", metroAvailability);
                    return localityJpaRepository.changeLocalityPopulationAndMetroAvailability(localityId, population, metroAvailability);
                } else {
                    log.info("Parameter attraction metro availability is null");
                    return localityJpaRepository.changeLocalityPopulation(localityId, population);
                }
            } else if (Objects.nonNull(metroAvailability)) {
                log.info("Parameter attraction population is null");
                return localityJpaRepository.changeLocalityMetroAvailability(localityId, metroAvailability);
            } else log.error("Parameters population and metro availability are nulls");
            throw new ProcessingException("Parameters population and metro availability are not specified");
        }
        log.error("Locality with Id: [{}] does not exist", localityId);
        throw new NotFoundException(String.format("Locality with Id: %s does not exist", localityId));
    }
}

