package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.Address;
import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.models.enumes.SurfaceType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TennisCourtRepositoryTest {

    @Autowired
    TennisCourtRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindObjectById() {
        //given
        TennisCourt tennisCourt = new TennisCourt(
                10,
                "Teren u Prigrevici",
                SurfaceType.CLAY,
                "Jako lep teren u Prigrevici",
                "slikaZaTerenUPrigrevici",
                null,
                null
                );
        TennisCourt savedTennisCourt = underTest.save(tennisCourt);
        //when
        TennisCourt expected = underTest.findById(savedTennisCourt.getId());
        //then
        assertThat(expected).isEqualTo(savedTennisCourt);
    }
}