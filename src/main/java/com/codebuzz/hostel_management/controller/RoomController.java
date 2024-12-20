package com.codebuzz.hostel_management.controller;

import com.codebuzz.hostel_management.exception.ResourceNotFoundException;
import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.model.Room;
import com.codebuzz.hostel_management.model.RoomOccupancyHistory;
import com.codebuzz.hostel_management.repository.RoomOccupancyHistoryRepository;
import com.codebuzz.hostel_management.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomOccupancyHistoryRepository roomOccupancyHistoryRepository;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        return new ResponseEntity<>(roomService.createRoom(room), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody Room room) {
        return roomService.updateRoom(id, room);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        return new ResponseEntity<>(roomService.getAvailableRooms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<Room> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    // delete a room by roomId
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{roomId}/assignResident/{residentId}")
    public Room assignResidentToRoom(@PathVariable Long roomId, @PathVariable Long residentId) {
        return roomService.assignResidentToRoom(roomId, residentId);
    }

    @PutMapping("/{roomId}/vacate/{residentId}")
    public ResponseEntity<String> vacateRoom(@PathVariable Long roomId, @PathVariable Long residentId) {
        try {
            roomService.vacateRoom(roomId, residentId);
            return ResponseEntity.ok("Resident removed from room successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/occupancy-history/{roomId}")
    public List<RoomOccupancyHistory> getRoomOccupancyHistory(@PathVariable Long roomId) {
        System.out.println("roomId = " + roomId);
        return roomOccupancyHistoryRepository.findByRoomId(roomId);
    }


}

