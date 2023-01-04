package com.tennis.tennisscheduler.services;

import com.tennis.tennisscheduler.models.TennisCourt;

import java.util.List;
import java.util.Optional;

public interface TennisCourtService {

    List<TennisCourt> getAllTennisCourts();

   Optional<TennisCourt> getTennisCourtById(long id);

    TennisCourt saveTennisCourt(TennisCourt tennisCourt);

    void deleteTennisCourtById(Long id);

    TennisCourt updateTennisCourt(long id, TennisCourt tennisCourt);
}
