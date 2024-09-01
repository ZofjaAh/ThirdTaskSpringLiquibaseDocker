package com.aston.thirdTask.infrastructure.database.repository.jpa;

import com.aston.thirdTask.infrastructure.database.entity.LocalityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing LocalityEntity.
 */
@Repository
public interface LocalityJpaRepository extends JpaRepository<LocalityEntity, Integer> {

    /**
     * Finds a locality by its name.
     *
     * @param localityName the name of the locality.
     * @return an Optional containing the found LocalityEntity, or an empty Optional if not found.
     */
    Optional<LocalityEntity> findByLocalityName(String localityName);

    /**
     * Changes the population and metro availability of a locality.
     *
     * @param localityId        the ID of the locality.
     * @param population        the new population.
     * @param metroAvailability the new metro availability status.
     * @return the number of records updated.
     */
    @Modifying
    @Query("""
            UPDATE LocalityEntity loc
            SET loc.population = :population, loc.metroAvailability = :metroAvailability
            WHERE loc.localityId = :localityId
            """)
    int changeLocalityPopulationAndMetroAvailability(@Param("localityId") Integer localityId,
                                                     @Param("population") Integer population,
                                                     @Param("metroAvailability") Boolean metroAvailability);

    /**
     * Changes the population of a locality.
     *
     * @param localityId the ID of the locality.
     * @param population the new population.
     * @return the number of records updated.
     */
    @Modifying
    @Query("""
            UPDATE LocalityEntity loc
            SET loc.population = :population
            WHERE loc.localityId = :localityId
            """
    )
    int changeLocalityPopulation(@Param("localityId") Integer localityId,
                                 @Param("population") Integer population);

    /**
     * Changes the metro availability of a locality.
     *
     * @param localityId        the ID of the locality.
     * @param metroAvailability the new metro availability status.
     * @return the number of records updated.
     */
    @Modifying
    @Query("""
            UPDATE LocalityEntity loc
            SET loc.metroAvailability = :metroAvailability
            WHERE loc.localityId = :localityId
            """
    )
    int changeLocalityMetroAvailability(@Param("localityId") Integer localityId,
                                        @Param("metroAvailability") Boolean metroAvailability);
}
