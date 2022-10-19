package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.models.enumes.SurfaceType;
import com.tennis.tennisscheduler.repositories.TennisCourtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TennisCourtServicesTest {

    @Mock
    private TennisCourtRepository tennisCourtRepository;
    private TennisCourtServices underTest;

    @BeforeEach
    void setUp() {
        underTest = new TennisCourtServices(tennisCourtRepository);
    }

    @Test
    void canGetAllTennisCourts() {
        TennisCourt tennisCourt1 = new TennisCourt(
                0,
                "Teren u Kacu",
                SurfaceType.CLAY,
                "Jako lep teren u Kacu",
                "slikaZaTerenUKacu",
                null,
                null
        );
        TennisCourt tennisCourt2 = new TennisCourt(
                1,
                "Teren u Prigrevici",
                SurfaceType.CLAY,
                "Jako lep teren u Prigrevici",
                "slikaZaTerenUPrigrevici",
                null,
                null
        );
        List<TennisCourt> tennisCourtList = new ArrayList<>();
        tennisCourtList.add(tennisCourt1);
        tennisCourtList.add(tennisCourt2);
        doReturn(tennisCourtList).when(tennisCourtRepository).findAll();

        List<TennisCourt> list2 = underTest.getAllTennisCourts();

        assertEquals(tennisCourtList,list2);
    }
    @Test
    void canSaveTennisCourt() {
        //given
        TennisCourt tennisCourt = new TennisCourt(
                0,
                "Teren u Prigrevici",
                SurfaceType.CLAY,
                "Jako lep teren u Prigrevici",
                "slikaZaTerenUPrigrevici",
                null,
                null
        );
        //when
        underTest.saveTennisCourt(tennisCourt);
        //then
        ArgumentCaptor<TennisCourt> tennisCourtArgumentCaptor =
                ArgumentCaptor.forClass(TennisCourt.class);
        verify(tennisCourtRepository).save(tennisCourtArgumentCaptor.capture());

        assertThat(tennisCourtArgumentCaptor.getValue()).isEqualTo(tennisCourt);
    }
    @Test
    void getTennisCourtById() {
        TennisCourt e1ForMock = new TennisCourt(
                0,
                "Teren u Prigrevici",
                SurfaceType.CLAY,
                "Jako lep teren u Prigrevici",
                "slikaZaTerenUPrigrevici",
                null,
                null
        );
        doReturn(e1ForMock).when(tennisCourtRepository).findById(0);
        // Make the service call
        TennisCourt e1ByService = underTest.getTennisCourtById(0);
        // Assert the response
        assertNotNull(e1ByService,"TennisCourt with this id: "+e1ForMock.getId()+" not found");
        assertEquals(e1ForMock.getId(),e1ByService.getId());
        assertEquals(e1ForMock.getName(), e1ByService.getName());
        assertEquals(e1ForMock.getSurfaceType(), e1ByService.getSurfaceType());
        assertEquals(e1ForMock.getDescription(), e1ByService.getDescription());
        assertEquals(e1ForMock.getImage(), e1ByService.getImage());
        assertEquals(e1ForMock.getTimeslot(), e1ByService.getTimeslot());
        assertEquals(e1ForMock.getAddress(), e1ByService.getAddress());
    }
    @Test
    void deleteTennisCourtById() {
        TennisCourt e1ForMock = new TennisCourt(
                0,
                "Teren u Prigrevici",
                SurfaceType.CLAY,
                "Jako lep teren u Prigrevici",
                "slikaZaTerenUPrigrevici",
                null,
                null
        );
        underTest.deleteTennisCourtById(e1ForMock.getId());

        verify(tennisCourtRepository).deleteById(e1ForMock.getId());
    }
    @Test
    void updateTennisCourt() {

        long id = 0L;

        TennisCourt existingTennisCourt = new TennisCourt(
                id,
                "Teren u Prigrevici",
                SurfaceType.CLAY,
                "Jako lep teren u Prigrevici",
                "slikaZaTerenUPrigrevici",
                null,
                null
        );

        when(tennisCourtRepository.findById(id))
                .thenReturn(existingTennisCourt);

        ArgumentCaptor<TennisCourt> tennisCourtArgumentCaptor =
                ArgumentCaptor.forClass(TennisCourt.class);

        TennisCourt updatedTennisCourt = new TennisCourt();
        updatedTennisCourt.setName("Teren u Kacu");
        underTest.updateTennisCourt(id,updatedTennisCourt);
        verify(tennisCourtRepository).save(tennisCourtArgumentCaptor.capture());

        assertThat(tennisCourtArgumentCaptor
                .getValue()
                .getName())
                .isEqualTo("Teren u Kacu");
    }
}