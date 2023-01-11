package com.tennis.tennisscheduler.service;

import com.tennis.tennisscheduler.exception.ApiRequestException;
import com.tennis.tennisscheduler.model.TennisCourt;
import com.tennis.tennisscheduler.repository.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TennisCourtService {
    private final TennisCourtRepository tennisCourtRepository;

    public List<TennisCourt> getAllTennisCourts(){
        return tennisCourtRepository.findAll();
    }

    public TennisCourt getTennisCourtById(long id){
        return tennisCourtRepository.findById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id isn't valid"));
    }

    public TennisCourt saveTennisCourt(TennisCourt tennisCourt){
        return tennisCourtRepository.save(tennisCourt);
    }

    public void deleteTennisCourtById(Long id){
        tennisCourtRepository.findById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id isn't valid"));
        tennisCourtRepository.deleteById(id);
    }

    public TennisCourt updateTennisCourt(long id, TennisCourt tennisCourt){
        TennisCourt existingTennisCourt = tennisCourtRepository.findById(id).orElseThrow(()->new ApiRequestException(HttpStatus.NOT_FOUND,"This id isn't valid"));
        existingTennisCourt.setName(tennisCourt.getName());
        existingTennisCourt.setDescription(tennisCourt.getDescription());
        existingTennisCourt.setImage(tennisCourt.getImage());
        existingTennisCourt.setSurfaceType(tennisCourt.getSurfaceType());
        existingTennisCourt.setWorkingTime(tennisCourt.getWorkingTime());
        existingTennisCourt.setAddress(tennisCourt.getAddress());

        return tennisCourtRepository.save(existingTennisCourt);
    }
}
