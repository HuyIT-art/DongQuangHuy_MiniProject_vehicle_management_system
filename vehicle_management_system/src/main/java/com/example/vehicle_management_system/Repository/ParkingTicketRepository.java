package com.example.vehicle_management_system.Repository;

import com.example.vehicle_management_system.DTO.Response.TicketResponse;
import com.example.vehicle_management_system.DTO.Response.TicketSummaryResponse;
import com.example.vehicle_management_system.Model.ParkingTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
    @Query("Select new com.example.vehicle_management_system.DTO.Response.TicketSummaryResponse(" +
            "p.id,v.licensePlate,z.name,p.checkInTime,p.checkOutTime) " +
            "from ParkingTicket p " +
            "join p.vehicle v " +
            "join p.zone z " +
            "where p.checkInTime >= :startDay and p.checkOutTime <= :finishDay")
    List<TicketSummaryResponse> getAllTicketsToday(@Param("startDay") LocalDateTime startDay, @Param("finishDay") LocalDateTime finishDay);

    @Query("SELECT new com.example.vehicle_management_system.DTO.Response.TicketResponse(" +
            "p.id, v.licensePlate, z.name, p.checkInTime, p.checkOutTime) " +
            "FROM ParkingTicket p " +
            "JOIN p.vehicle v " +
            "JOIN p.zone z " +
            "WHERE (:licensePlate IS NULL OR v.licensePlate LIKE :licensePlate) " +
            "AND p.checkInTime >= :fromDate AND p.checkInTime <= :toDate")
    Page<TicketResponse> getAllTicketsInDuration(
            @Param("licensePlate") String licensePlate,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );

    //Lay tat ca danh sach ticket
    @Query("Select new com.example.vehicle_management_system.DTO.Response.TicketResponse(" +
            "p.id, v.licensePlate, z.name, p.checkInTime,p.checkOutTime)  " +
            "from ParkingTicket p " +
            "join p.vehicle v " +
            "join p.zone z")
    List<TicketResponse> findAllTickets();
}

//and ((:fromDate is null and :toDate is null) or (p.checkInTime >= :fromDate and p.checkOutTime <= :toDate))")
