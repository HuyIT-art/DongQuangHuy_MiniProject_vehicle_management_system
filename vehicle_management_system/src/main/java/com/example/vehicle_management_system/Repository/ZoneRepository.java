package com.example.vehicle_management_system.Repository;

import com.example.vehicle_management_system.DTO.Response.ZoneStatisticsResponse;
import com.example.vehicle_management_system.Model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    @Query("select new com.example.vehicle_management_system.DTO.Response.ZoneStatisticsResponse(z.id,z.name,z.capacity,z.occupiedSpots,(z.capacity-z.occupiedSpots)) " +
            "from Zone z")
    List<ZoneStatisticsResponse> getZoneStatistics();
}
