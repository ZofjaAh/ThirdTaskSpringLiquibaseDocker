package com.aston.thirdTask.buisness;

import com.aston.thirdTask.api.dto.AttractionDTO;
import com.aston.thirdTask.buisness.dao.AttractionDAO;
import com.aston.thirdTask.domain.Attraction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AttractionService {

    private final AttractionDAO attractionDAO;


    @Transactional
    public Attraction create(Attraction attraction) {
            return attractionDAO.create(attraction);

}
@Transactional
    public List<Attraction> findAttractionsByLocalityName(String localityName) {

        return attractionDAO.findAllByLocalityName(localityName).stream().toList();
    }
@Transactional
    public List<Attraction> findAttractionsWithSortingAndFilter(String sortingDirection, String typeFilter) {
        return attractionDAO.findAllWithSortingAndFilter(sortingDirection,typeFilter);
    }
@Transactional
    public int changeAttractionDescription(Integer attractionId, String description) {

        return attractionDAO.changeAttractionDescription(attractionId, description);
    }
@Transactional
    public void deleteAttraction(Integer attractionId) {
        attractionDAO.deleteAttraction(attractionId);
    }
}
