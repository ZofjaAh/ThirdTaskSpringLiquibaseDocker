package com.aston.thirdTask.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface LocalDateMapper {

    DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Named("mapOffsetDateTimeToString")
    default String mapLocalDateToString(LocalDate localDate){
        return Optional.ofNullable(localDate)
                .map(date-> date.format(LOCAL_DATE_FORMAT))
                .orElse(null);
    }
    @Named("mapStringToLocalDate")
    default LocalDate mapStringToLocalDate(String date){
        LocalDate localDate = Optional.ofNullable(date)
                .map(str -> LocalDate.parse(str, LOCAL_DATE_FORMAT))
                .orElse(null);

       return localDate;
    }
    }
