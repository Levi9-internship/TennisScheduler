package com.tennis.tennisscheduler.repository;

import com.tennis.tennisscheduler.model.TennisCourt;
import com.tennis.tennisscheduler.model.enumes.SurfaceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TennisCourtRepositoryTest {

    @Autowired
    TennisCourtRepository tennisCourtRepository;

    @AfterEach
    void tearDown() {
        tennisCourtRepository.deleteAll();
    }

    @Test
    void itShouldFindObjectById() {
        //given

        TennisCourt tennisCourt = TennisCourt.builder()
                .id(0)
                .name("Teren u Prigrevici")
                .surfaceType(SurfaceType.CLAY)
                .description("Jako lep teren u Prigrevici")
                .image("slikaZaTerenUPrigrevici")
                .timeslot(null)
                .address(null)
                .build();

        TennisCourt savedTennisCourt = tennisCourtRepository.save(tennisCourt);
        //when
        TennisCourt expected = tennisCourtRepository.findById(savedTennisCourt.getId()).get();
        //then
        assertThat(expected).isEqualTo(savedTennisCourt);
    }
}