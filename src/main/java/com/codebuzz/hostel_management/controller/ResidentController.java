package com.codebuzz.hostel_management.controller;

import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {
    @Autowired
    private ResidentService residentService;

    @PostMapping
    public ResponseEntity<Resident> registerResident(@RequestBody Resident resident) {
        return new ResponseEntity<>(residentService.registerResident(resident), HttpStatus.CREATED);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Resident>> getActiveResidents() {
        return new ResponseEntity<>(residentService.getAllActiveResidents(), HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateResident(@PathVariable Long id) {
        residentService.deactivateResident(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

