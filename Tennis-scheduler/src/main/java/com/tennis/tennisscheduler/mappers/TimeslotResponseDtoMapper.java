package com.tennis.tennisscheduler.mappers;

import com.tennis.tennisscheduler.dtos.TimeslotResponseDto;
import com.tennis.tennisscheduler.response.TimeslotResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TimeslotResponseDtoMapper {

    @Mapping(source = "message", target = "message")
    @Mapping(source = "timeslot.id", target = "timeslot.id")
    @Mapping(source = "timeslot.startDate", target = "timeslot.dateStart")
    @Mapping(source = "timeslot.endDate", target = "timeslot.dateEnd")
    @Mapping(source = "timeslot.person.id", target = "timeslot.personId")
    @Mapping(source = "timeslot.tennisCourt.id", target = "timeslot.courtId")
    TimeslotResponseDto toTimeslotResponseDto(TimeslotResponse timeslot);

}
