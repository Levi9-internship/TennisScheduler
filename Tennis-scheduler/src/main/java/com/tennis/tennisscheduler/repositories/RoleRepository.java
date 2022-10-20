package com.tennis.tennisscheduler.repositories;

import com.tennis.tennisscheduler.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
