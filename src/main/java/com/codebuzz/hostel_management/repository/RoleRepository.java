package com.codebuzz.hostel_management.repository;

import com.codebuzz.hostel_management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Find a role by its name
    Optional<Role> findByName(String name);
}
