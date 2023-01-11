package com.tennis.tennisscheduler.mapper;

import com.tennis.tennisscheduler.dto.TennisCourtDto;
import com.tennis.tennisscheduler.model.TennisCourt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TennisCourtDtoMapper {
    @Mapping(source = "workingTime", target = "workingTimeDto")
    TennisCourtDto fromTennisCourtToTennisCourtDto (TennisCourt tennisCourt);
    @Mapping(source = "workingTimeDto", target = "workingTime")
    TennisCourt fromTennisCourtDtoToTennisCourt (TennisCourtDto tennisCourtDto);
}
