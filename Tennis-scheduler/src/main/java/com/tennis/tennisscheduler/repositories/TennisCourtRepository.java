package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.TennisCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TennisCourtRepository extends JpaRepository<TennisCourt,Long> {
    Optional<TennisCourt> findById(long id);
}
