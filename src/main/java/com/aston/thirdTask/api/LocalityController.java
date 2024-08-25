package com.aston.thirdTask.api;

import com.aston.thirdTask.api.dto.AttractionDTO;
import com.aston.thirdTask.api.dto.AttractionsDTO;
import com.aston.thirdTask.api.dto.LocalityDTO;
import com.aston.thirdTask.api.dto.mapper.AttractionMapper;
import com.aston.thirdTask.api.dto.mapper.LocalityMapper;
import com.aston.thirdTask.buisness.AttractionService;
import com.aston.thirdTask.buisness.LocalityService;
import com.aston.thirdTask.domain.Attraction;
import com.aston.thirdTask.domain.Locality;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(LocalityController.REST_LOCALITY)
public class LocalityController {
    public static final String REST_LOCALITY= "/rest/locality";
    public static final String CREATE_LOCALITY= "/create";
    public static final String CHANGE_LOCALITY = "/change/{localityId}";

    private final LocalityService localityService;

    private final LocalityMapper localityMapper;
    @PostMapping(CREATE_LOCALITY)
    public LocalityDTO createLocality(
             @RequestBody LocalityDTO localityDTO){
      return localityMapper.map(localityService.create(localityMapper.map(localityDTO)));
    }

    @PatchMapping(CHANGE_LOCALITY)
    public LocalityDTO changeLocality(
            @PathVariable Integer localityId,
           @RequestParam Integer population,
           @RequestParam Boolean metroAvailability) {
        return localityMapper.map(
                localityService.changeLocality(localityId, population, metroAvailability));

    }
}


