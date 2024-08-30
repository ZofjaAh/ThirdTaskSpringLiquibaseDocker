package com.aston.thirdTask.infrastructure.database.repository.jpa;

import com.aston.thirdTask.domain.Locality;
import com.aston.thirdTask.infrastructure.database.entity.LocalityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalityJpaRepository extends JpaRepository<LocalityEntity, Integer> {

    Optional<LocalityEntity> findByLocalityName(String localityName);

    @Modifying
    @Query("""
            UPDATE LocalityEntity loc
            SET loc.population = :population, loc.metroAvailability = :metroAvailability
            WHERE loc.localityId = :localityId
            """)
    int changeLocalityPopulationAndMetroAvailability(@Param("localityId") Integer localityId,
                                                     @Param("population") Integer population,
                                                     @Param("metroAvailability") Boolean metroAvailability);

    @Modifying
    @Query("""
            UPDATE LocalityEntity loc
            SET loc.population = :population
            WHERE loc.localityId = :localityId
            """
    )
    int changeLocalityPopulation(@Param("localityId") Integer localityId,
                                 @Param("population") Integer population);

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
