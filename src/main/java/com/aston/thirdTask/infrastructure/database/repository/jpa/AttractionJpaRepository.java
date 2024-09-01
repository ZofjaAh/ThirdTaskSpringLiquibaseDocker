package com.aston.thirdTask.infrastructure.database.repository.jpa;

import com.aston.thirdTask.infrastructure.database.entity.AttractionEntity;
import com.aston.thirdTask.infrastructure.database.entity.AttractionType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Repository interface for managing AttractionEntity.
 */
@Repository
public interface AttractionJpaRepository extends JpaRepository<AttractionEntity, Integer> {


    /**
     * Finds attractions by locality name.
     *
     * @param name the name of the locality.
     * @return a set of AttractionEntity.
     */
    @Query("""
            SELECT att FROM AttractionEntity att
             LEFT JOIN FETCH att.locality locality
             WHERE locality.localityName = :name
            """)
    Set<AttractionEntity> findByLocalityName(@Param("name") String name);

    /**
     * Finds all attractions with sorting and filtering by type.
     *
     * @param type the type of the attraction.
     * @param sort the sorting criteria.
     * @return a list of AttractionEntity.
     */
    @Query("""
            SELECT att FROM AttractionEntity att
            WHERE att.type = :type
            """)
    List<AttractionEntity> findAllWithSortAndFilter(@Param("type") AttractionType type,
                                                    Sort sort);

    /**
     * Finds all attractions with filtering by type.
     *
     * @param type the type of the attraction.
     * @return a list of AttractionEntity.
     */
    @Query("SELECT att FROM AttractionEntity att WHERE att.type = :type")
    List<AttractionEntity> findAllWithFilter(@Param("type") AttractionType type);

    /**
     * Finds all attractions with sorting.
     *
     * @param sort the sorting criteria.
     * @return a list of AttractionEntity.
     */
    @Query("""
            SELECT att FROM AttractionEntity att
            """)
    List<AttractionEntity> findAllWithSorting(Sort sort);

    /**
     * Changes the description of an attraction by its ID.
     *
     * @param attractionId the ID of the attraction.
     * @param description  the new description.
     * @return the number of records updated.
     */
    @Modifying
    @Query("""
            UPDATE AttractionEntity att
            SET att.description = :description 
            WHERE att.attractionId = :attractionId
            """
    )
    int changeAttractionDescriptionById(@Param("attractionId") Integer attractionId,
                                        @Param("description") String description);

    /**
     * Deletes attraction services by attraction ID.
     *
     * @param attractionId the ID of the attraction.
     */
    @Modifying
    @Query(value = "DELETE FROM attraction_service WHERE attraction_id = :attractionId", nativeQuery = true)
    void deleteAttractionServicesByAttractionId(@Param("attractionId") Integer attractionId);
}
