package com.aston.thirdTask.buisness.dao;

import com.aston.thirdTask.domain.Attraction;

import java.util.List;
import java.util.Set;

public interface AttractionDAO {

    Attraction create(Attraction attraction);

    Set<Attraction> findAllByLocalityName(String localityName);

    List<Attraction> findAllWithSortingAndFilter(String sortingDirection, String typeFilter);


    int changeAttractionDescription(Integer attractionId, String description);

    void deleteAttraction(Integer attractionId);
}
