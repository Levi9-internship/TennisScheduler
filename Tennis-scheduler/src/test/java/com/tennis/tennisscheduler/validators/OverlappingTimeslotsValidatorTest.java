package com.tennis.tennisscheduler.validators;

import com.tennis.tennisscheduler.dtos.TimeslotDto;
import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.repositories.TimeslotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OverlappingTimeslotsValidatorTest {

    ConstraintValidatorContext constraintValidatorContext;
    ConstraintValidator overlappingTimeslotsValidator;
    @Mock
    TimeslotRepository timeslotRepository;
    private List<Timeslot> timeslots = new ArrayList<>();
    private TennisCourt tennisCourt;

    @BeforeEach
    void setUp() {
        overlappingTimeslotsValidator = new OverlappingTimeslotsValidator(timeslotRepository);
        setUpData();
    }

    void setUpData(){
        tennisCourt = new TennisCourt();
        tennisCourt.setId(0);
        Timeslot timeslot = Timeslot.builder()
                .startDate(new Date(2022, 12,12,18,30))
                .endDate(new Date(2022, 12,12,19,30))
                .tennisCourt(tennisCourt)
                .build();
        timeslots.add(timeslot);
    }

    @Test
    void overlappingWithEndOfExistingTimeslot(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new Date(2022, 12,12,18,30))
                .dateEnd(new Date(2022, 12,12,22,30))
                .courtId(tennisCourt.getId())
                .build();

        doReturn(timeslots).when(timeslotRepository).checkOverlapping(timeslotDto.getDateStart(),
               timeslotDto.getDateEnd(), tennisCourt.getId(), 0L);

        assertFalse(overlappingTimeslotsValidator.isValid(timeslotDto, constraintValidatorContext));
    }

    @Test
    void overlappingWithStartOfExistingTimeslot(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new Date(2022, 12,12,19,00))
                .dateEnd(new Date(2022, 12,12,19,45))
                .courtId(tennisCourt.getId())
                .build();

        doReturn(timeslots).when(timeslotRepository).checkOverlapping(timeslotDto.getDateStart(),
                timeslotDto.getDateEnd(), tennisCourt.getId(), 0L);

        assertFalse(overlappingTimeslotsValidator.isValid(timeslotDto, constraintValidatorContext));
    }

    @Test
    void overlappingWithMiddleOfExistingTimeslot(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new Date(2022, 12,12,18,40))
                .dateEnd(new Date(2022, 12,12,19,15))
                .courtId(tennisCourt.getId())
                .build();

        doReturn(timeslots).when(timeslotRepository).checkOverlapping(timeslotDto.getDateStart(),
                timeslotDto.getDateEnd(), tennisCourt.getId(), 0L);

        assertFalse(overlappingTimeslotsValidator.isValid(timeslotDto, constraintValidatorContext));
    }

    @Test
    void overlappingWithStartAndEndOfExistingTimeslot(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new Date(2022, 12,12,18,00))
                .dateEnd(new Date(2022, 12,12,20,00))
                .courtId(tennisCourt.getId())
                .build();

        doReturn(timeslots).when(timeslotRepository).checkOverlapping(timeslotDto.getDateStart(),
                timeslotDto.getDateEnd(), tennisCourt.getId(), 0L);

        assertFalse(overlappingTimeslotsValidator.isValid(timeslotDto, constraintValidatorContext));
    }

    @Test
    void isValid(){
        TimeslotDto timeslotDto = TimeslotDto.builder()
                .dateStart(new Date(2022, 12,12,18,30))
                .dateEnd(new Date(2022, 12,12,22,30))
                .courtId(tennisCourt.getId())
                .build();

        doReturn(new ArrayList<>()).when(timeslotRepository).checkOverlapping(timeslotDto.getDateStart(),
                timeslotDto.getDateEnd(), tennisCourt.getId(), 0L);

        assertTrue(overlappingTimeslotsValidator.isValid(timeslotDto, constraintValidatorContext));
    }

}