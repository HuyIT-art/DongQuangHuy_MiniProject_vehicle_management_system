package com.example.vehicle_management_system.Controller;

import com.example.vehicle_management_system.DTO.Request.TicketRequest;
import com.example.vehicle_management_system.DTO.Response.ApiResponse;
import com.example.vehicle_management_system.DTO.Response.PageResponse;
import com.example.vehicle_management_system.DTO.Response.TicketResponse;
import com.example.vehicle_management_system.DTO.Response.TicketSummaryResponse;
import com.example.vehicle_management_system.Model.ParkingTicket;
import com.example.vehicle_management_system.Repository.ParkingTicketRepository;
import com.example.vehicle_management_system.Service.ParkingTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class ParkingTicketController {
    private final ParkingTicketRepository parkingTicketRepository;
    private final ParkingTicketService parkingTicketService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<TicketResponse>>> findAllParkingTickets(){
        List<TicketResponse> list = parkingTicketService.findAllTickets();
        ApiResponse<List<TicketResponse>> response = ApiResponse.<List<TicketResponse>>builder()
                .message("Lay danh sach thanh cong!")
                .success(true)
                .data(list)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    //Tao ParkingTicket
    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<TicketResponse>> checkIn(@RequestBody TicketRequest ticketRequest) {
        TicketResponse ticketResponse = parkingTicketService.checkIn(ticketRequest);
        ApiResponse<TicketResponse> response = ApiResponse.<TicketResponse>builder()
                .message("Check-in thanh cong!")
                .success(true)
                .data(ticketResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/check-out/{vehicleId}")
    public ResponseEntity<ApiResponse<TicketResponse>> checkOut(@PathVariable("vehicleId") Long vehicleId) {
        TicketResponse ticketResponse = parkingTicketService.checkOut(vehicleId);
        ApiResponse<TicketResponse> response = ApiResponse.<TicketResponse>builder()
                .message("Check-out thanh cong!")
                .success(true)
                .data(ticketResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    //Lay toan bo ve hom nay
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<TicketSummaryResponse>>> getAllTicketsToday(){
        List<TicketSummaryResponse> list = parkingTicketService.getAllTicketsToday();
        ApiResponse<List<TicketSummaryResponse>> response = ApiResponse.<List<TicketSummaryResponse>>builder()
                .message("Lay danh sach thanh cong!")
                .data(list)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    //Lay ve theo bien so va khoang thoi gian
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<PageResponse<TicketResponse>>>  getAllTicketsInDuration(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "checkInTime") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction  direction,
            @RequestParam(required = false) String licensePlate,
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate
            ){
        PageResponse<TicketResponse> pageResponse = parkingTicketService.getAllTicketsInDuration(page,size,sortBy,direction,licensePlate,fromDate,toDate);
        ApiResponse<PageResponse<TicketResponse>> response = ApiResponse.<PageResponse<TicketResponse>>builder()
                .message("Lay danh sach thanh cong!")
                .data(pageResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
