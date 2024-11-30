package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.repository.GrievanceRepository;
import com.codebuzz.hostel_management.repository.ResidentRepository;
import com.codebuzz.hostel_management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private GrievanceRepository grievanceRepository;

    public long countActiveResidents() {
        return residentRepository.findByStatus("Active").size();
    }

    public long countAvailableRooms() {
        return roomRepository.findByStatus("Available").size();
    }

    public long countPendingGrievances() {
        return grievanceRepository.countByStatus("Pending");
    }
}

