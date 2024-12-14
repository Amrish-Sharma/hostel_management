package com.codebuzz.hostel_management.controller;

import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.service.ResidentService;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<Resident> addResident(@RequestBody Resident resident) {
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

    @GetMapping("/csv/export")
    public ResponseEntity<byte[]> exportToCSV() throws IOException {
        return service.exportToCSV();
    }
}

