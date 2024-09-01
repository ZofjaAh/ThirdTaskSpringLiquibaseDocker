package com.aston.thirdTask.api;

import com.aston.thirdTask.api.dto.LocalityDTO;
import com.aston.thirdTask.api.dto.mapper.LocalityMapper;
import com.aston.thirdTask.buisness.LocalityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing localities.
 */
@RestController
@AllArgsConstructor
@RequestMapping(LocalityController.REST_LOCALITY)
public class LocalityController {
    public static final String REST_LOCALITY = "/rest/locality";
    public static final String CREATE_LOCALITY = "/create";
    public static final String CHANGE_LOCALITY = "/change/{localityId}";

    private final LocalityService localityService;
    private final LocalityMapper localityMapper;

    /**
     * Creates a new locality.
     *
     * @param localityDTO DTO of the locality to be created.
     * @return DTO of the created locality.
     */
    @PostMapping(CREATE_LOCALITY)
    public LocalityDTO createLocality(
            @RequestBody LocalityDTO localityDTO) {
        return localityMapper.map(localityService.create(localityMapper.map(localityDTO)));
    }

    /**
     * Changes the details of a locality.
     *
     * @param localityId        ID of the locality.
     * @param population        New population (optional).
     * @param metroAvailability New metro availability status (optional).
     * @return Number of records updated.
     */
    @PatchMapping(CHANGE_LOCALITY)
    public int changeLocality(
            @PathVariable Integer localityId,
            @RequestParam(required = false) Integer population,
            @RequestParam(required = false) Boolean metroAvailability) {
        return localityService.changeLocality(localityId, population, metroAvailability);

    }
}


