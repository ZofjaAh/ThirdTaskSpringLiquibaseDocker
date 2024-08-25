package com.aston.thirdTask.infrastructure.database.repository.jpa;

import com.aston.thirdTask.domain.Locality;
import com.aston.thirdTask.infrastructure.database.entity.LocalityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
    LocalityEntity changeLocalityPopulationAndMetroAvailability(Integer localityId, Integer population, Boolean metroAvailability);

    @Modifying
    @Query("""
            UPDATE LocalityEntity loc
            SET loc.population = :population
            WHERE loc.localityId = :localityId
            """
    )
    LocalityEntity changeLocalityPopulation(Integer localityId, Integer population);

    @Modifying
    @Query("""
            UPDATE LocalityEntity loc
            SET loc.metroAvailability = :metroAvailability
            WHERE loc.localityId = :localityId
            """
    )
    LocalityEntity changeLocalityMetroAvailability(Integer localityId, Boolean metroAvailability);
}
