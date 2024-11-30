package com.codebuzz.hostel_management.repository;

import com.codebuzz.hostel_management.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
    List<Resident> findByStatus(String status);
}

