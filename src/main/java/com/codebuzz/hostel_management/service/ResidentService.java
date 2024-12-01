package com.codebuzz.hostel_management.service;

import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.opencsv.CSVWriter;


import java.io.FileWriter;
import java.io.IOException;
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

    public void exportResidentsToCSV() {
        List<Resident> residents = getAllResidents();
        try (CSVWriter writer = new CSVWriter(new FileWriter("residents.csv"))) {
            // Write header
            writer.writeNext(new String[]{"ID", "Name", "Email", "Phone", "Address", "Status", "Room ID"});

            // Write data
            for (Resident resident : residents) {
                writer.writeNext(new String[]{
                        resident.getId().toString(),
                        resident.getName(),
                        resident.getEmail(),
                        resident.getPhone(),
                        resident.getAddress(),
                        resident.getStatus(),
                        resident.getRoom() != null ? resident.getRoom().getId().toString() : ""
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}


