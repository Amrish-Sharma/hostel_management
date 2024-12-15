package com.codebuzz.hostel_management.repository;

import com.codebuzz.hostel_management.model.Room;
import com.codebuzz.hostel_management.model.Resident;
import com.codebuzz.hostel_management.model.RoomOccupancyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomOccupancyHistoryRepository extends JpaRepository<RoomOccupancyHistory, Long> {
    @Query("SELECT roh FROM RoomOccupancyHistory roh WHERE roh.room.roomId = :roomId")
    List<RoomOccupancyHistory> findByRoomId(Long roomId);

    Optional<RoomOccupancyHistory> findByRoomAndResidentAndVacatedAtIsNull(Room room, Resident resident);
}


