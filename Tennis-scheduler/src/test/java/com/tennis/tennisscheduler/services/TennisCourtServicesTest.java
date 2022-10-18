package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.repositories.TennisCourtRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
//https://youtu.be/Geq60OVyBPg?t=3105
class TennisCourtServicesTest {

    @Mock
    private TennisCourtRepository tennisCourtRepository;
    private AutoCloseable autoCloseable;
    private TennisCourtServices underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new TennisCourtServices(tennisCourtRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllTennisCourts() {
        //when
        underTest.getAllTennisCourts();
        //then
        verify(tennisCourtRepository).findAll();
    }

    @Test
    @Disabled
    void getTennisCourtById() {
    }

    @Test
    @Disabled
    void saveTennisCourt() {
    }

    @Test
    @Disabled
    void deleteTennisCourtById() {
    }

    @Test
    @Disabled
    void updateTennisCourt() {
    }
}