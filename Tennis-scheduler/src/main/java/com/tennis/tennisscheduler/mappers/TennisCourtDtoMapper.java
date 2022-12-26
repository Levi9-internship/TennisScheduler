package com.tennis.tennisscheduler.mappers;

import com.tennis.tennisscheduler.dtos.TennisCourtDto;
import com.tennis.tennisscheduler.models.TennisCourt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TennisCourtDtoMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surfaceType", target = "surfaceType")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "workingTime", target = "workingTimeDto")
    TennisCourtDto fromTennisCourtToTennisCourtDto (TennisCourt tennisCourt);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surfaceType", target = "surfaceType")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "workingTimeDto", target = "workingTime")
    TennisCourt fromTennisCourtDtoToTennisCourt (TennisCourtDto tennisCourtDto);
}
