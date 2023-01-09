package com.tennis.tennisscheduler.repository;

import com.tennis.tennisscheduler.model.TennisCourt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TennisCourtRepository extends JpaRepository<TennisCourt,Long> {
    Optional<TennisCourt> findById(long id);
}
