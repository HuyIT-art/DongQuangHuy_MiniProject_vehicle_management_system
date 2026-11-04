package com.example.vehicle_management_system.Repository;

import com.example.vehicle_management_system.DTO.Response.VehicleResponse;
import com.example.vehicle_management_system.Enum.VehicleType;
import com.example.vehicle_management_system.Model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("Select new com.example.vehicle_management_system.DTO.Response.VehicleResponse(v.id,v.licensePlate,v.color,v.type) " +
            "from Vehicle v " +
            "where (:keyword is null or v.type = :keyword)")
    Page<VehicleResponse> findAllByKeyword(@Param("keyword") VehicleType keyword, Pageable pageable);

    @Query("Select new com.example.vehicle_management_system.DTO.Response.VehicleResponse(v.id,v.licensePlate,v.color,v.type) " +
            "from Vehicle v ")
    Page<VehicleResponse> findAllVehicles(Pageable pageable);
}
