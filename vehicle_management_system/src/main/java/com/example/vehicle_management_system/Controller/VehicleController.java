package com.example.vehicle_management_system.Controller;

import com.example.vehicle_management_system.DTO.Response.ApiResponse;
import com.example.vehicle_management_system.DTO.Response.PageResponse;
import com.example.vehicle_management_system.DTO.Response.VehicleResponse;
import com.example.vehicle_management_system.Enum.VehicleType;
import com.example.vehicle_management_system.Model.Vehicle;
import com.example.vehicle_management_system.Repository.VehicleRepository;
import com.example.vehicle_management_system.Service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    private final VehicleRepository vehicleRepository;

    //Lay danh sach phuong tien
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<VehicleResponse>>> findAllVehicle(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Sort.Direction direction
    ){
        PageResponse<VehicleResponse> pageResponse = vehicleService.findAll(page, size, sortBy, direction);
        ApiResponse<PageResponse<VehicleResponse>> response = ApiResponse.<PageResponse<VehicleResponse>>builder()
                .message("Lay danh sach thanh cong!")
                .success(true)
                .data(pageResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    //Them doi tuong vehicle
    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle){
         vehicleRepository.save(vehicle);
         return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(vehicle);
    }

    //Tim kiem xe tho the loai xe
    @GetMapping("/by-type")
    public ResponseEntity<ApiResponse<PageResponse<VehicleResponse>>> getPagedVehicles(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Sort.Direction direction,
            @RequestParam(required = false) VehicleType keyword
    ){
        PageResponse<VehicleResponse> pageResponse = vehicleService.getPagedVehicles(page, size, sortBy, direction, keyword);
        ApiResponse<PageResponse<VehicleResponse>> response = ApiResponse.<PageResponse<VehicleResponse>>builder()
                .message("Lay danh sach thanh cong!")
                .success(true)
                .data(pageResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
