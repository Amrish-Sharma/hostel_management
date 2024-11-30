package com.codebuzz.hostel_management.controller;

import com.codebuzz.hostel_management.model.Room;
import com.codebuzz.hostel_management.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        return new ResponseEntity<>(roomService.createRoom(room), HttpStatus.CREATED);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        return new ResponseEntity<>(roomService.getAvailableRooms(), HttpStatus.OK);
    }
}

