package com.aston.thirdTask.buisness;

import com.aston.thirdTask.buisness.dao.AttractionDAO;
import com.aston.thirdTask.domain.Attraction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing attractions.
 */
@Service
@AllArgsConstructor
public class AttractionService {

    private final AttractionDAO attractionDAO;

    /**
     * Creates a new attraction.
     *
     * @param attraction the attraction to be created.
     * @return the created attraction.
     */
    @Transactional
    public Attraction create(Attraction attraction) {
        return attractionDAO.create(attraction);
    }

    /**
     * Finds attractions by locality name.
     *
     * @param localityName the name of the locality.
     * @return a list of attractions in the specified locality.
     */
    @Transactional
    public List<Attraction> findAttractionsByLocalityName(String localityName) {
        return attractionDAO.findAllByLocalityName(localityName).stream().toList();
    }

    /**
     * Finds attractions with sorting and filtering.
     *
     * @param sortingDirection the direction of sorting (e.g., "asc" or "desc").
     * @param typeFilter       the type filter for attractions.
     * @return a list of attractions that match the sorting and filtering criteria.
     */
    @Transactional
    public List<Attraction> findAttractionsWithSortingAndFilter(String sortingDirection, String typeFilter) {
        return attractionDAO.findAllWithSortingAndFilter(sortingDirection, typeFilter);
    }

    /**
     * Changes the description of an attraction.
     *
     * @param attractionId the ID of the attraction.
     * @param description  the new description.
     * @return the number of records updated.
     */
    @Transactional
    public int changeAttractionDescription(Integer attractionId, String description) {

        return attractionDAO.changeAttractionDescription(attractionId, description);
    }

    /**
     * Deletes an attraction.
     *
     * @param attractionId the ID of the attraction to be deleted.
     */
    @Transactional
    public void deleteAttraction(Integer attractionId) {
        attractionDAO.deleteAttraction(attractionId);
    }
}
