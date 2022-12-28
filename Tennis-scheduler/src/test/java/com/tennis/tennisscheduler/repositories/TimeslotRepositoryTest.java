package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.Address;
import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.models.Timeslot;
import com.tennis.tennisscheduler.models.enumes.SurfaceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class TimeslotRepositoryTest {

    @Autowired
    TimeslotRepository timeslotRepository;

    @Autowired
    TennisCourtRepository tennisCourtRepository;

    private long tennisCourtId;

    @AfterEach
    void tearDown() {
        timeslotRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        TennisCourt tennisCourt = new TennisCourt().builder()
                .id(0L)
                .name("Teren u Sapcu")
                .description("Mnogo zanimljivih ljudi")
                .surfaceType(SurfaceType.CLAY)
                .address(Address.builder()
                        .city("Sabac")
                        .country("Srbija")
                        .street("Cerska")
                        .number(23)
                        .build())
                .build();
        tennisCourtId = tennisCourtRepository.save(tennisCourt).getId();

        Timeslot timeslot = Timeslot.builder()
                .startDate(new Date(2022, 12,12,19,45))
                .endDate(new Date(2022, 12,12,20,30))
                .tennisCourt(tennisCourt)
                .build();
        timeslotRepository.save(timeslot);
    }

    @Test
    void checkOverlappingWithStartOfExistingTimeslot(){
        List<Timeslot> expected = timeslotRepository.overlappingWithStartOfExistingTimeslot(new Date(2022, 12,12,18,0), new Date(2022, 12,12,20,0), tennisCourtId, 100L);

        assertThat(expected).isEqualTo(timeslotRepository.findAll());
    }

    @Test
    void overlappingWithEndOfExistingTimeslot(){
        List<Timeslot> expected = timeslotRepository.overlappingWithEndOfExistingTimeslot(new Date(2022, 12,12,20,0), new Date(2022, 12,12,21,0), tennisCourtId, 100L);

        assertThat(expected).isEqualTo(timeslotRepository.findAll());
    }

    @Test
    void overlappingWithMiddleOfExistingTimeslot(){
        List<Timeslot> expected = timeslotRepository.overlappingWithMiddleOfExistingTimeslot(new Date(2022, 12,12,20,0), new Date(2022, 12,12,20,15), tennisCourtId, 100L);

        assertThat(expected).isEqualTo(timeslotRepository.findAll());
    }

    @Test
    void overlappingWithStartAndEndOfExistingTimeslot(){
        List<Timeslot> expected = timeslotRepository.overlappingWithStartAndEndOfExistingTimeslot(new Date(2022, 12,12,19,0), new Date(2022, 12,12,20,45), tennisCourtId, 100L);

        assertThat(expected).isEqualTo(timeslotRepository.findAll());
    }

}
