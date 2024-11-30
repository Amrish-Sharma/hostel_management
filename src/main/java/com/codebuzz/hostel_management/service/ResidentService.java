package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentService {
    @Autowired
    private ResidentRepository residentRepository;

    public Resident registerResident(Resident resident) {
        return residentRepository.save(resident);
    }

    public List<Resident> getAllActiveResidents() {
        return residentRepository.findByStatus("Active");
    }

    public void deactivateResident(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found"));
        resident.setStatus("Inactive");
        residentRepository.save(resident);
    }
}

