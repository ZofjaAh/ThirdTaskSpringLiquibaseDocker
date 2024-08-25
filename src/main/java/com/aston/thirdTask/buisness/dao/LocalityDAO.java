package com.aston.thirdTask.buisness.dao;

import com.aston.thirdTask.domain.Attraction;
import com.aston.thirdTask.domain.Locality;

import java.util.Optional;

public interface LocalityDAO {

    Locality create(Locality locality);

    Optional<Locality> findLocalityByName(String localityName);

    Locality changeLocality(Integer localityId, Integer population, Boolean metroAvailability);
}
