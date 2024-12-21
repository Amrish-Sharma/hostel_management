package com.codebuzz.hostel_management.controller;

import com.codebuzz.hostel_management.model.Grievance;
import com.codebuzz.hostel_management.model.Room;
import com.codebuzz.hostel_management.service.GrievanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grievances")
public class GrievanceController {
    @Autowired
    private GrievanceService grievanceService;

    @PostMapping
    public ResponseEntity<Grievance> reportGrievance(@RequestBody Grievance grievance) {
        return new ResponseEntity<>(grievanceService.reportGrievance(grievance), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Grievance>> getAllGrievances() {
        return new ResponseEntity<>(grievanceService.getAllGrievances(), HttpStatus.OK);
    }
    @GetMapping("/{residentId}")
    public ResponseEntity<List<Grievance>> getGrievances(@PathVariable Long residentId) {
        return new ResponseEntity<>(grievanceService.getGrievancesByResident(residentId), HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Grievance> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return new ResponseEntity<>(grievanceService.updateGrievanceStatus(id, status), HttpStatus.OK);
    }
}

