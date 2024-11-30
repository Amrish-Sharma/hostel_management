package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.Grievance;
import com.codebuzz.hostel_management.repository.GrievanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GrievanceService {
    @Autowired
    private GrievanceRepository grievanceRepository;

    public Grievance reportGrievance(Grievance grievance) {
        grievance.setCreatedAt(LocalDateTime.now());
        grievance.setStatus("Pending");
        return grievanceRepository.save(grievance);
    }

    public List<Grievance> getGrievancesByResident(Long residentId) {
        return grievanceRepository.findByResidentId(residentId);
    }

    public Grievance updateGrievanceStatus(Long id, String status) {
        Grievance grievance = grievanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grievance not found"));
        grievance.setStatus(status);
        return grievanceRepository.save(grievance);
    }
}

