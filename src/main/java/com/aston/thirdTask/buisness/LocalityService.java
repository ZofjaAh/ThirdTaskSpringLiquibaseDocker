package com.aston.thirdTask.buisness;

import com.aston.thirdTask.buisness.dao.AttractionDAO;
import com.aston.thirdTask.buisness.dao.LocalityDAO;
import com.aston.thirdTask.domain.Attraction;
import com.aston.thirdTask.domain.Locality;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Service
@AllArgsConstructor
public class LocalityService {

    private final LocalityDAO localityDAO;
@Transactional
    public Locality findLocalityByName(String localityName) {
        Optional<Locality> locality = localityDAO.findLocalityByName(localityName);
        if (locality.isEmpty()) {
            throw new RuntimeException("Could not find locality by Locality Name: [%s]".formatted(localityName));
        }
       log.info("Find locality [{}]", locality);
        return locality.get();
    }

    @Transactional
    public Locality create(Locality locality) {
            return localityDAO.create(locality);

}
@Transactional

    public int changeLocality(Integer localityId, Integer population, Boolean metroAvailability) {

    return localityDAO.changeLocality(localityId, population, metroAvailability);
    }
}
