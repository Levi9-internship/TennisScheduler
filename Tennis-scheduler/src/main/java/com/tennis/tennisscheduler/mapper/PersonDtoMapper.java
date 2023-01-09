package com.tennis.tennisscheduler.mapper;

import com.tennis.tennisscheduler.dto.PersonDto;
import com.tennis.tennisscheduler.model.Person;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PersonDtoMapper {
    PersonDto fromPersonToPersonDto (Person person);
    Person fromPersonDtoToPerson (PersonDto personDto);
}
