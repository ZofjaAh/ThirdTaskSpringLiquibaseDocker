package com.aston.thirdTask.infrastructure.database.repository.jpa;

import com.aston.thirdTask.domain.Attraction;
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

@Repository
public interface AttractionJpaRepository extends JpaRepository<AttractionEntity, Integer> {


    @Query("""
            SELECT att FROM AttractionEntity att
             LEFT JOIN FETCH att.locality locality
             WHERE locality.localityName = :name
             """)
    Set<AttractionEntity> findByLocalityName(@Param("name") String name);

    @Query("""
            SELECT att FROM AttractionEntity att
            WHERE att.type = :type
            """)
    List<AttractionEntity> findAllWithSortAndFilter(@Param("type") AttractionType type,
                                                    Sort sort);

    @Query("SELECT att FROM AttractionEntity att WHERE att.type = :type")
    List<AttractionEntity> findAllWithFilter(@Param("type") AttractionType type);

    @Query("""
            SELECT att FROM AttractionEntity att
            """)
    List<AttractionEntity> findAllWithSorting(Sort sort);
@Modifying
    @Query("""
            UPDATE AttractionEntity att
            SET att.description = :description 
            WHERE att.attractionId = :attractionId
            """
    )
    int changeAttractionDescriptionById(@Param("attractionId")Integer attractionId,
                                               @Param("description") String description);
    @Modifying
    @Query(value = "DELETE FROM attraction_service WHERE attraction_id = :attractionId", nativeQuery = true)
    void deleteAttractionServicesByAttractionId(@Param("attractionId") Integer attractionId);
}
