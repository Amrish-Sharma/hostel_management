package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.Grievance;
import com.codebuzz.hostel_management.model.Room;
import com.codebuzz.hostel_management.repository.GrievanceRepository;
import com.codebuzz.hostel_management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GrievanceService {
    @Autowired
    private GrievanceRepository grievanceRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Grievance reportGrievance(Grievance grievance) {
        grievance.setCreatedAt(LocalDateTime.now());
        grievance.setStatus("Pending");

        // If it's a room maintenance grievance, validate the room
        if ("Room Maintenance".equalsIgnoreCase(grievance.getType()) && grievance.getRoom() != null) {
            Room room = roomRepository.findById(grievance.getRoom().getRoomId())
                    .orElseThrow(() -> new RuntimeException("Room not found"));
            grievance.setRoom(room);
        }

        return grievanceRepository.save(grievance);
    }

    public List<Grievance> getGrievancesByResident(Long residentId) {
        return grievanceRepository.findByResidentId(residentId);
    }

    public List<Grievance> getAllGrievances() {
        return grievanceRepository.findAll();
    }

    public Grievance updateGrievanceStatus(Long id, String status) {
        Grievance grievance = grievanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grievance not found"));

        grievance.setStatus(status);

        // Optional: Notify the resident for resolved maintenance requests
        if ("Resolved".equalsIgnoreCase(status) && "Room Maintenance".equalsIgnoreCase(grievance.getType())) {
            // Add notification logic here
            System.out.println("Maintenance request resolved for Room: " + grievance.getRoom().getRoomId());
        }

        return grievanceRepository.save(grievance);
    }

    public List<Grievance> getGrievancesByRoom(Long roomId) {
        return grievanceRepository.findByRoomRoomId(roomId);
    }



    public List<Grievance> getGrievancesByType(String type) {
        return grievanceRepository.findByType(type);
    }

}

