package com.aston.thirdTask.api;

import com.aston.thirdTask.api.dto.AttractionDTO;
import com.aston.thirdTask.api.dto.AttractionsDTO;
import com.aston.thirdTask.api.dto.mapper.AttractionMapper;
import com.aston.thirdTask.buisness.AttractionService;
import com.aston.thirdTask.buisness.LocalityService;
import com.aston.thirdTask.domain.Attraction;
import com.aston.thirdTask.domain.Locality;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AttractionController.REST_ATTRACTION)
public class AttractionController {
    public static final String REST_ATTRACTION= "/rest/attraction";
    public static final String CREATE_ATTRACTION= "/create";
    public static final String SHOW_ATTRACTIONS_BY_LOCATION = "/show/{localityName}";
    public static final String CHANGE_ATTRACTION_DESCRIPTION = "/change/{attractionId}";
    public static final String SHOW_ALL_ATTRACTIONS = "/show_all";
    public static final String DELETE_ATTRACTION = "/delete/{attractionId}";

    private final AttractionService attractionService;
    private final LocalityService localityService;

    private final AttractionMapper attractionMapper;
    @PostMapping(CREATE_ATTRACTION)
    public AttractionDTO createAttraction(
             @RequestBody AttractionDTO attractionDTO){
        log.info("Catch request on [{}]  with body [{}]", CREATE_ATTRACTION, attractionDTO);
        Attraction newAttraction = attractionMapper.map(attractionDTO);
        Locality locality = localityService.findLocalityByName(attractionDTO.getLocalityName());
        Set<Attraction> attractions = new HashSet<>();
        if(Objects.isNull(locality.getAttractions())){
            attractions.add(newAttraction);
        } else {
            attractions.addAll(locality.getAttractions());
            attractions.add(newAttraction);
        }
        Locality localityToSave = locality.withAttractions(attractions);
        return attractionMapper.map(attractionService.create(newAttraction.withLocality(localityToSave)));
    }
    @GetMapping(value = SHOW_ATTRACTIONS_BY_LOCATION)
    public AttractionsDTO showAttractionsByLocalityName(
            @PathVariable("localityName") String localityName){
        localityService.findLocalityByName(localityName);
        return createAttractions(attractionService.findAttractionsByLocalityName(localityName));

    }
    @GetMapping(value = SHOW_ALL_ATTRACTIONS)
    public AttractionsDTO showAllAttractionsWithSortingAndFilter(
           @RequestParam(required = false) String sortingDirection,
            @RequestParam(required = false) String typeFilter){
       return createAttractions(attractionService.findAttractionsWithSortingAndFilter(sortingDirection,typeFilter));

    }
    @PatchMapping(CHANGE_ATTRACTION_DESCRIPTION)
    public int changeAttractionDescription(
            @PathVariable Integer attractionId,
           @RequestParam String description){
        return attractionService.changeAttractionDescription(attractionId, description);

    }
    @DeleteMapping(DELETE_ATTRACTION)
    public void deleteAttraction(
            @PathVariable Integer attractionId){
        attractionService.deleteAttraction(attractionId);
    }

    private AttractionsDTO createAttractions(List<Attraction> attractionsByLocality) {
        return AttractionsDTO.builder()
                .attractions(attractionsByLocality.stream()
                        .map(attractionMapper::mapToAttractionWithService)
                        .toList())
                .build();
    }
}


