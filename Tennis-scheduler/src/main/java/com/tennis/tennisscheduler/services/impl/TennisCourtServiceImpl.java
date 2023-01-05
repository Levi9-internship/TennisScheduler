package com.tennis.tennisscheduler.services.impl;

import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.repositories.TennisCourtRepository;
import com.tennis.tennisscheduler.services.TennisCourtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TennisCourtServiceImpl implements TennisCourtService {
    private final TennisCourtRepository tennisCourtRepository;

    @Override
    public List<TennisCourt> getAllTennisCourts(){
        return tennisCourtRepository.findAll();
    }

    @Override
    public Optional<TennisCourt> getTennisCourtById(long id){

        return tennisCourtRepository.findById(id);
    }

    @Override
    public TennisCourt saveTennisCourt(TennisCourt tennisCourt){

        return tennisCourtRepository.save(tennisCourt);
    }

    @Override
    public void deleteTennisCourtById(Long id){
        tennisCourtRepository.deleteById(id);
    }

    @Override
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
