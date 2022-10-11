package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.TennisCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TennisCourtRepository extends JpaRepository<TennisCourt,Long> {
}
