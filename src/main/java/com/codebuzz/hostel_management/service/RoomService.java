package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.model.Room;
import com.codebuzz.hostel_management.repository.RoomRepository;
import com.codebuzz.hostel_management.repository.ResidentRepository;
import com.codebuzz.hostel_management.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ResidentRepository residentRepository;

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

    public Room assignResidentToRoom(Long roomId, Long residentId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException("Resident not found"));

        // Check if the room has capacity for the new resident
        if (room.getOccupied() >= room.getCapacity()) {
            throw new IllegalArgumentException("Room capacity exceeded");
        }

        // Check if the room type allows adding this resident
        // Add the resident to the room's resident list
        room.getResidents().add(resident);

        // Update the number of occupied slots
        room.setOccupied(room.getOccupied() + 1);

        // Update room status if fully occupied
        if (room.getOccupied() == room.getCapacity()) {
            room.setStatus("Occupied");
        } else {
            room.setStatus("Available");
        }

        // Save the room
        return roomRepository.save(room);
    }


}
