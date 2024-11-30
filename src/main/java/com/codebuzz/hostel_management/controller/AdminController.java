package com.codebuzz.hostel_management.controller;

import com.codebuzz.hostel_management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Long>> getDashboard() {
        Map<String, Long> dashboardData = new HashMap<>();
        dashboardData.put("activeResidents", adminService.countActiveResidents());
        dashboardData.put("availableRooms", adminService.countAvailableRooms());
        dashboardData.put("pendingGrievances", adminService.countPendingGrievances());
        return new ResponseEntity<>(dashboardData, HttpStatus.OK);
    }
}

