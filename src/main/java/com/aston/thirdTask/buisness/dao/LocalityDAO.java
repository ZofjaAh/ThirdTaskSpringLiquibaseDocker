package com.aston.thirdTask.buisness.dao;

import com.aston.thirdTask.domain.Locality;

import java.util.Optional;

public interface LocalityDAO {

    Locality create(Locality locality);

    Optional<Locality> findLocalityByName(String localityName);

    int changeLocality(Integer localityId, Integer population, Boolean metroAvailability);
}
