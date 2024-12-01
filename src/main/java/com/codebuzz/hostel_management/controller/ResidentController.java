package com.codebuzz.hostel_management.controller;

import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {
    @Autowired
    private ResidentService service;

    @GetMapping
    public List<Resident> getAllResidents() {
        return service.getAllResidents();
    }

    @PostMapping
    public Resident addResident(@RequestBody Resident resident) {
        return service.addResident(resident);
    }

    @PutMapping("/{id}")
    public Resident updateResident(@PathVariable Long id, @RequestBody Resident resident) {
        return service.updateResident(id, resident);
    }

    @GetMapping("/{id}")
    public Optional<Resident> getResidentById(@PathVariable Long id) {
        return service.getResidentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteResident(@PathVariable Long id) {
        service.deleteResident(id);
    }
}

