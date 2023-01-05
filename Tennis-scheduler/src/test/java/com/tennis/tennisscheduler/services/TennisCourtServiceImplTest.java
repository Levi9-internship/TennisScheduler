package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.models.enumes.SurfaceType;
import com.tennis.tennisscheduler.repositories.TennisCourtRepository;
import com.tennis.tennisscheduler.services.impl.TennisCourtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TennisCourtServiceImplTest {

    @Mock
    private TennisCourtRepository tennisCourtRepository;
    private TennisCourtService tennisCourtService;

    @BeforeEach
    void setUp() {
        tennisCourtService = new TennisCourtServiceImpl(tennisCourtRepository);
    }

    @Test
    void canGetAllTennisCourts() {

        TennisCourt tennisCourt1 = TennisCourt.builder()
                .id(0)
                .name("Teren u Prigrevici")
                .surfaceType(SurfaceType.CLAY)
                .description("Jako lep teren u Prigrevici")
                .image("slikaZaTerenUPrigrevici")
                .timeslot(null)
                .address(null)
                .build();

        TennisCourt tennisCourt2 = TennisCourt.builder()
                .id(0)
                .name("Teren u Kacu")
                .surfaceType(SurfaceType.CLAY)
                .description("Jako lep teren u Kacu")
                .image("slikaZaTerenUKacu")
                .timeslot(null)
                .address(null)
                .build();

        List<TennisCourt> tennisCourtList = new ArrayList<>();
        tennisCourtList.add(tennisCourt1);
        tennisCourtList.add(tennisCourt2);
        doReturn(tennisCourtList).when(tennisCourtRepository).findAll();
        List<TennisCourt> list2 = tennisCourtService.getAllTennisCourts();

        assertEquals(tennisCourtList,list2);
    }
    @Test
    void canSaveTennisCourt() {
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
        //when
        tennisCourtService.saveTennisCourt(tennisCourt);
        //then
        ArgumentCaptor<TennisCourt> tennisCourtArgumentCaptor =
                ArgumentCaptor.forClass(TennisCourt.class);
        verify(tennisCourtRepository).save(tennisCourtArgumentCaptor.capture());

        assertThat(tennisCourtArgumentCaptor.getValue()).isEqualTo(tennisCourt);
    }
    @Test
    void getTennisCourtById() {
         Optional<TennisCourt> tennisCourt = Optional.of(TennisCourt.builder()
                .id(0)
                .name("Teren u Prigrevici")
                .surfaceType(SurfaceType.CLAY)
                .description("Jako lep teren u Prigrevici")
                .image("slikaZaTerenUPrigrevici")
                .timeslot(null)
                .address(null)
                .build());

        doReturn(tennisCourt).when(tennisCourtRepository).findById(0);
        // Make the service call
        TennisCourt tennisCourtByService = tennisCourtService.getTennisCourtById(0).get();
        // Assert the response
        assertNotNull(tennisCourtByService,"TennisCourt with this id: "+tennisCourt.get().getId()+" not found");
        assertEquals(tennisCourt.get().getId(),tennisCourtByService.getId());
        assertEquals(tennisCourt.get().getName(), tennisCourtByService.getName());
        assertEquals(tennisCourt.get().getSurfaceType(), tennisCourtByService.getSurfaceType());
        assertEquals(tennisCourt.get().getDescription(), tennisCourtByService.getDescription());
        assertEquals(tennisCourt.get().getImage(), tennisCourtByService.getImage());
        assertEquals(tennisCourt.get().getTimeslot(), tennisCourtByService.getTimeslot());
        assertEquals(tennisCourt.get().getAddress(), tennisCourtByService.getAddress());
    }
    @Test
    void deleteTennisCourtById() {
        TennisCourt tennisCourt = TennisCourt.builder()
                .id(0)
                .name("Teren u Prigrevici")
                .surfaceType(SurfaceType.CLAY)
                .description("Jako lep teren u Prigrevici")
                .image("slikaZaTerenUPrigrevici")
                .timeslot(null)
                .address(null)
                .build();

        tennisCourtService.deleteTennisCourtById(tennisCourt.getId());

        verify(tennisCourtRepository).deleteById(tennisCourt.getId());
    }
    @Test
    void updateTennisCourt() {

        long id = 0L;

        Optional<TennisCourt> existingTennisCourt = Optional.of(TennisCourt.builder()
                .id(id)
                .name("Teren u Prigrevici")
                .surfaceType(SurfaceType.CLAY)
                .description("Jako lep teren u Prigrevici")
                .image("slikaZaTerenUPrigrevici")
                .timeslot(null)
                .address(null)
                .build());

        when(tennisCourtRepository.findById(id))
                .thenReturn(existingTennisCourt);

        ArgumentCaptor<TennisCourt> tennisCourtArgumentCaptor =
                ArgumentCaptor.forClass(TennisCourt.class);

        TennisCourt updatedTennisCourt = new TennisCourt();
        updatedTennisCourt.setName("Teren u Kacu");
        updatedTennisCourt.setImage("novaSlika");
        updatedTennisCourt.setDescription("Jako lep teren u Kacu");
        updatedTennisCourt.setSurfaceType(SurfaceType.GRASS);
        tennisCourtService.updateTennisCourt(id,updatedTennisCourt);
        verify(tennisCourtRepository).save(tennisCourtArgumentCaptor.capture());

        assertThat(tennisCourtArgumentCaptor
                .getValue()
                .getName())
                .isEqualTo("Teren u Kacu");
        assertThat(tennisCourtArgumentCaptor
                .getValue()
                .getImage())
                .isEqualTo("novaSlika");
        assertThat(tennisCourtArgumentCaptor
                .getValue()
                .getDescription())
                .isEqualTo("Jako lep teren u Kacu");
        assertThat(tennisCourtArgumentCaptor
                .getValue()
                .getSurfaceType())
                .isEqualTo(SurfaceType.GRASS);
    }
}