package com.codebuzz.hostel_management.repository;
import com.codebuzz.hostel_management.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStatus(String status);
}
