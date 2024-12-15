package com.codebuzz.hostel_management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoomOccupancyHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    private LocalDateTime allottedAt;
    private LocalDateTime vacatedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public LocalDateTime getAllottedAt() {
        return allottedAt;
    }

    public void setAllottedAt(LocalDateTime allottedAt) {
        this.allottedAt = allottedAt;
    }

    public LocalDateTime getVacatedAt() {
        return vacatedAt;
    }

    public void setVacatedAt(LocalDateTime vacatedAt) {
        this.vacatedAt = vacatedAt;
    }
}
