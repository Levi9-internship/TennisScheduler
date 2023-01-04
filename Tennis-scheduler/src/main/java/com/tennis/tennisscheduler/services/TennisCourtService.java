package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.repositories.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TennisCourtService {
    private final TennisCourtRepository tennisCourtRepository;

    public List<TennisCourt> getAllTennisCourts(){

        return tennisCourtRepository.findAll();
    }

    public Optional<TennisCourt> getTennisCourtById(long id){

        return tennisCourtRepository.findById(id);
    }

    public TennisCourt saveTennisCourt(TennisCourt tennisCourt){

        return tennisCourtRepository.save(tennisCourt);
    }

    public void deleteTennisCourtById(Long id){
        tennisCourtRepository.deleteById(id);
    }

    public TennisCourt updateTennisCourt(long id, TennisCourt tennisCourt){
        TennisCourt existingTennisCourt = this.tennisCourtRepository.findById(id).get();
        existingTennisCourt.setName(tennisCourt.getName());
        existingTennisCourt.setDescription(tennisCourt.getDescription());
        existingTennisCourt.setImage(tennisCourt.getImage());
        existingTennisCourt.setSurfaceType(tennisCourt.getSurfaceType());
        existingTennisCourt.setWorkingTime(tennisCourt.getWorkingTime());

        existingTennisCourt.setAddress(tennisCourt.getAddress());

        return tennisCourtRepository.save(existingTennisCourt);
    }
}
