package com.example.vehicle_management_system.Service;

import com.example.vehicle_management_system.DTO.Request.TicketRequest;
import com.example.vehicle_management_system.DTO.Response.PageResponse;
import com.example.vehicle_management_system.DTO.Response.TicketResponse;
import com.example.vehicle_management_system.DTO.Response.TicketSummaryResponse;
import com.example.vehicle_management_system.ExceptionCenter.BadRequestException;
import com.example.vehicle_management_system.ExceptionCenter.NotFoundException;
import com.example.vehicle_management_system.Model.ParkingTicket;
import com.example.vehicle_management_system.Model.Vehicle;
import com.example.vehicle_management_system.Model.Zone;
import com.example.vehicle_management_system.Repository.ParkingTicketRepository;
import com.example.vehicle_management_system.Repository.VehicleRepository;
import com.example.vehicle_management_system.Repository.ZoneRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingTicketService {
    private final ParkingTicketRepository parkingTicketRepository;
    private final VehicleRepository vehicleRepository;
    private final ZoneRepository zoneRepository;

    //CheckIn phuong tien
    @Transactional
    public TicketResponse checkIn(TicketRequest req){
        Vehicle vehicle = vehicleRepository.findById(req.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle Not Found"));
        Zone zone = zoneRepository.findById(req.getZoneId())
                .orElseThrow(() -> new NotFoundException("Zone Not Found"));
        if((zone.getCapacity() - zone.getOccupiedSpots()) < 0){
            throw new BadRequestException("Capacity Not Enough");
        }
        LocalDateTime checkInTime = LocalDateTime.now();
        ParkingTicket parkingTicket = ParkingTicket.builder()
                .vehicle(vehicle)
                .zone(zone)
                .checkInTime(checkInTime)
                .build();
        parkingTicket = parkingTicketRepository.save(parkingTicket);
        zone.setOccupiedSpots(zone.getOccupiedSpots() + 1);
        TicketResponse ticketResponse = TicketResponse.builder()
                .id(parkingTicket.getId())
                .licensePlate(parkingTicket.getVehicle().getLicensePlate())
                .zoneName(zone.getName())
                .checkInTime(parkingTicket.getCheckInTime())
                .build();
        return ticketResponse;
    }
    //CheckOut phuong tien
    @Transactional
    public TicketResponse checkOut(Long vehicleId){
        ParkingTicket parkingTicket =  parkingTicketRepository.findById(vehicleId).orElseThrow(() -> new NotFoundException("Vehicle Not Found"));

        //Tao bill check out
        parkingTicket.setCheckOutTime(LocalDateTime.now());
        parkingTicket.getZone().setOccupiedSpots(parkingTicket.getZone().getOccupiedSpots() - 1);
        TicketResponse ticketResponse = TicketResponse.builder()
                .id(parkingTicket.getId())
                .licensePlate(parkingTicket.getVehicle().getLicensePlate())
                .zoneName(parkingTicket.getZone().getName())
                .checkInTime(parkingTicket.getCheckInTime())
                .checkOutTime(parkingTicket.getCheckOutTime())
                .build();
        return ticketResponse;
    }

    //Lay cac ve trong ngay hom nay
    public List<TicketSummaryResponse> getAllTicketsToday(){
        LocalDateTime startDay =  LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).minusDays(1);
        LocalDateTime finishDay = startDay.plusDays(1);

        List<TicketSummaryResponse> list =  parkingTicketRepository.getAllTicketsToday(startDay,finishDay);
        return list;
    }

    //Lay danh sach xe theo bien so va theo khoang thoi gian
    public PageResponse<TicketResponse> getAllTicketsInDuration(int page, int size, String sortBy, Sort.Direction direction, String licensePlate, LocalDateTime fromDate, LocalDateTime toDate) {
        if(page < 0){
            page = 0;
        }
        if(size < 0){
            size = 0;
        }
        Sort sort = Sort.unsorted();
        if(direction != null && sortBy != null) {
            sort = Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        // Nếu từ client không truyền fromDate/toDate thì gán mốc mặc định
        LocalDateTime start = (fromDate != null) ? fromDate : LocalDateTime.of(1970, 1, 1, 0, 0);
        LocalDateTime end = (toDate != null) ? toDate : LocalDateTime.now();

        // Truyền start và end xuống Repository
        Page<TicketResponse> list = parkingTicketRepository.getAllTicketsInDuration(
                licensePlate, start, end, pageable
        );
        PageResponse<TicketResponse> pageResponse = PageResponse.<TicketResponse>builder()
                .items(list.getContent())
                .page(list.getNumber())
                .size(list.getSize())
                .totalPages(list.getTotalPages())
                .totalItems(list.getTotalElements())
                .isLast(list.isLast())
                .build();
        return pageResponse;
    }

    //Lay tat ca cac danh sach ve xe
    public List<TicketResponse> findAllTickets(){
        return parkingTicketRepository.findAllTickets();
    }
}
