package com.aston.thirdTask.buisness;

import com.aston.thirdTask.buisness.dao.LocalityDAO;
import com.aston.thirdTask.domain.Locality;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for managing localities.
 */
@Slf4j
@Service
@AllArgsConstructor
public class LocalityService {

    private final LocalityDAO localityDAO;

    /**
     * Finds a locality by its name.
     *
     * @param localityName the name of the locality.
     * @return the found locality.
     * @throws RuntimeException if the locality is not found.
     */
    @Transactional
    public Locality findLocalityByName(String localityName) {
        Optional<Locality> locality = localityDAO.findLocalityByName(localityName);
        if (locality.isEmpty()) {
            throw new RuntimeException("Could not find locality by Locality Name: [%s]".formatted(localityName));
        }
        log.info("Find locality [{}]", locality);
        return locality.get();
    }

    /**
     * Creates a new locality.
     *
     * @param locality the locality to be created.
     * @return the created locality.
     */
    @Transactional
    public Locality create(Locality locality) {
        return localityDAO.create(locality);

    }

    /**
     * Changes the details of a locality.
     *
     * @param localityId        the ID of the locality.
     * @param population        the new population (optional).
     * @param metroAvailability the new metro availability status (optional).
     * @return the number of records updated.
     */
    @Transactional

    public int changeLocality(Integer localityId, Integer population, Boolean metroAvailability) {

        return localityDAO.changeLocality(localityId, population, metroAvailability);
    }
}
