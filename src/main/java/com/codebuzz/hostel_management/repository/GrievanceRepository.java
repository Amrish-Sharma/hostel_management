package com.codebuzz.hostel_management.repository;

import com.codebuzz.hostel_management.model.Grievance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrievanceRepository extends JpaRepository<Grievance, Long> {
    List<Grievance> findByResidentId(Long residentId);

    long countByStatus(String pending);
}

