package com.tennis.tennisscheduler.repositories;


import com.tennis.tennisscheduler.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
}
