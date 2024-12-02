package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.Room;
import com.codebuzz.hostel_management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room room) {
        Room existingRoom = roomRepository.findById(id).orElse(null);
        if (existingRoom != null) {
            existingRoom.setType(room.getType());
            existingRoom.setRent(room.getRent());
            existingRoom.setCapacity(room.getCapacity());
            existingRoom.setOccupied(room.getOccupied());
            existingRoom.setStatus(room.getStatus());
            return roomRepository.save(existingRoom);
        }
        return null;
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByStatus("Available");
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id){
        return roomRepository.findById(id);
    }

    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }
}
