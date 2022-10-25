package com.tennis.tennisscheduler.mappers;

import com.tennis.tennisscheduler.dtos.PersonDto;
import com.tennis.tennisscheduler.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PersonDtoMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "password", target = "password")
    PersonDto fromPersonToPersonDto (Person person);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "password", target = "password")
    Person fromPersonDtoToPerson (PersonDto personDto);
}
