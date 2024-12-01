package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.repository.ResidentRepository;
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

    public List<Resident> getAllResidents() {
        return repository.findAll();
    }

    public Optional<Resident> getResidentById(Long Id) {
        return repository.findById(Id);
    }

    public Resident addResident(Resident resident) {
        return repository.save(resident);
    }

    public Resident updateResident(Long id, Resident resident) {
        resident.setId(id);
        return repository.save(resident);
    }

    public void deleteResident(Long id) {
        repository.deleteById(id);
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
                    resident.getRoom().getRoomId().toString(),
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



