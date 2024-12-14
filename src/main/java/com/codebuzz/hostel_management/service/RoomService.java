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
        // Fetch the room by ID or throw exception if not found
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
        // Fetch the resident by ID or throw exception if not found
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException("Resident not found with ID: " + residentId));
        // Check if the room has available capacity
        if (room.getOccupied() >= room.getCapacity()) {
            throw new IllegalArgumentException("Cannot assign resident: Room capacity exceeded");
        }

        // Assign resident to room
        resident.setRoomId(roomId);
        residentRepository.save(resident);

        room.getResidents().add(resident);

        System.out.println("roomResidents After Addition"+room.getResidents().stream().toString());


        // Update room occupancy and status
        room.setOccupied(room.getOccupied() + 1);
        room.setStatus(room.getOccupied() == room.getCapacity() ? "Occupied" : "Available");

        // Save and return the updated room

        return roomRepository.save(room);
    }

    public void vacateRoom(Long roomId, Long residentId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));

        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException("Resident not found with ID: " + residentId));

        // Remove resident from the room
        if (room.getResidents().remove(resident)) {
            // Update room occupancy
            room.setOccupied(room.getOccupied() - 1);

            // Update room status
            if (room.getOccupied() == 0) {
                room.setStatus("Available");
            }

            roomRepository.save(room);

            // Optionally, clear the room assignment in Resident entity
            resident.setRoom(null);
            residentRepository.save(resident);
        } else {
            throw new IllegalArgumentException("Resident is not assigned to this room.");
        }
    }


}
