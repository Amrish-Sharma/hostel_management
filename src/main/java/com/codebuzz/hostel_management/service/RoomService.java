package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.Room;
import com.codebuzz.hostel_management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByStatus("Available");
    }
}
