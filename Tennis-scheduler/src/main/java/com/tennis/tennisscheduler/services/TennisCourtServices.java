package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.TennisCourt;
import com.tennis.tennisscheduler.repositories.AddressRepository;
import com.tennis.tennisscheduler.repositories.TennisCourtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TennisCourtServices {
    private final TennisCourtRepository tennisCourtRepository;
    private final AddressRepository addressRepository;
    public TennisCourtServices(TennisCourtRepository tennisCourtRepository, AddressRepository addressRepository) {
        this.tennisCourtRepository = tennisCourtRepository;
        this.addressRepository = addressRepository;
    }

    public List<TennisCourt> getAllTennisCourts(){

        return tennisCourtRepository.findAll();
    }

    public TennisCourt getTennisCourtById(long id){

        return tennisCourtRepository.findById(id);
    }

    public TennisCourt saveTennisCourt(TennisCourt tennisCourt){

        return tennisCourtRepository.save(tennisCourt);
    }

    public void deleteTennisCourtById(Long id){
        tennisCourtRepository.deleteById(id);
    }

    public TennisCourt updateTennisCourt(long id, TennisCourt tennisCourt){
        TennisCourt existingTennisCourt = this.tennisCourtRepository.findById(id);
        existingTennisCourt.setName(tennisCourt.getName());
        existingTennisCourt.setDescription(tennisCourt.getDescription());
        existingTennisCourt.setImage(tennisCourt.getImage());
        existingTennisCourt.setSurfaceType(tennisCourt.getSurfaceType());

        existingTennisCourt.setAddress(tennisCourt.getAddress());

        return tennisCourtRepository.save(existingTennisCourt);
    }
}
