package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.exception.ResourceNotFoundException;
import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.model.Room;
import com.codebuzz.hostel_management.repository.ResidentRepository;
import com.codebuzz.hostel_management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.opencsv.*;


import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class ResidentService {
    @Autowired
    private ResidentRepository repository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    public List<Resident> getAllResidents() {
        return repository.findAll();
    }

    public Optional<Resident> getResidentById(Long Id) {
        return repository.findById(Id);
    }

    public ResponseEntity<Resident> addResident(Resident resident) {
        System.out.println("I AM GETTING THIS VALUE " + resident.getRoomId());

        if (resident.getRoomId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Room ID is required
        }

        try {
            Long roomId = Long.parseLong(resident.getRoomId().toString());
            System.out.println("Room ID: " + roomId);

            // Save the resident
            Resident savedResident = repository.save(resident);

            // Assign the resident to the room
            roomService.assignResidentToRoom(roomId, savedResident.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedResident);

        } catch (NumberFormatException e) {
            System.err.println("Invalid Room ID format: " + resident.getRoomId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ResourceNotFoundException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    public Resident updateResident(Long id, Resident resident) {
        Resident existingResident = repository.findById(id).orElse(null);
        if (existingResident == null) {
            throw new IllegalArgumentException("Resident ID does not exist");
        }

        Room oldRoom = existingResident.getRoom();
        Room newRoom = resident.getRoom();

        if (newRoom != null && !roomRepository.existsById(newRoom.getRoomId())) {
            throw new IllegalArgumentException("Room ID does not exist");
        }

        if (oldRoom != null && !oldRoom.equals(newRoom)) {
            oldRoom.setOccupied(oldRoom.getOccupied() - 1);
            oldRoom.setStatus("Available");
            roomRepository.save(oldRoom);
        }

        if (newRoom != null) {
            newRoom = roomRepository.findById(newRoom.getRoomId()).orElse(null);
            if (newRoom != null) {
                if (newRoom.getOccupied() >= newRoom.getCapacity()) {
                    throw new IllegalArgumentException("Room capacity exceeded");
                }
                newRoom.setOccupied(newRoom.getOccupied() + 1);
                if (newRoom.getOccupied() == newRoom.getCapacity()) {
                    newRoom.setStatus("Occupied");
                } else {
                    newRoom.setStatus("Available");
                }
                roomRepository.save(newRoom);
            }
        }

        resident.setId(id);
        return repository.save(resident);
    }

    public void deleteResident(Long id) {
        Resident resident = repository.findById(id).orElse(null);
        if (resident != null) {
            Room room = resident.getRoom();
            if (room != null) {
                room.setOccupied(room.getOccupied() - 1);
                room.setStatus("Available");
                roomRepository.save(room);
            }
            repository.deleteById(id);
        }
    }

    public ResponseEntity<byte[]> exportToCSV() throws IOException {
        // Fetch the list of residents from the database
        List<Resident> residents = repository.findAll();

        // Prepare an in-memory output stream for CSV data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        CSVWriter csvWriter = new CSVWriter(writer);

        // Define the CSV header
        String[] header = {"ID", "Name", "Room Number", "Email", "Phone", "Status"};
        csvWriter.writeNext(header);

        // Write resident data rows
        for (Resident resident : residents) {
            String[] residentData = {
                    String.valueOf(resident.getId()),
                    resident.getName(),
                    resident.getRoomId().toString(),
                    resident.getEmail(),
                    resident.getPhone(),
                    resident.getStatus()
            };
            csvWriter.writeNext(residentData);
        }

        // Flush and close the CSVWriter
        csvWriter.flush();

        // Convert the output to a byte array
        byte[] csvData = outputStream.toByteArray();

        // Return the byte array as a downloadable CSV file
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=residents.csv")
                .contentType(org.springframework.http.MediaType.parseMediaType("text/csv"))
                .body(csvData);
    }

}



