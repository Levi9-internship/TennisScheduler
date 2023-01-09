package com.tennis.tennisscheduler.repository;

import com.tennis.tennisscheduler.model.Role;
import com.tennis.tennisscheduler.model.enumes.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(UserType userType);
}
