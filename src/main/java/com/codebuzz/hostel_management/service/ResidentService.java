package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentService {
    @Autowired
    private ResidentRepository repository;

    public List<Resident> getAllResidents() {
        return repository.findAll();
    }

    public Optional<Resident> getResidentById(Long Id) {
        return repository.findById(Id);
    }

    public Resident addResident(Resident resident) {
        return repository.save(resident);
    }

    public Resident updateResident(Long id, Resident resident) {
        resident.setId(id);
        return repository.save(resident);
    }

    public void deleteResident(Long id) {
        repository.deleteById(id);
    }
}

