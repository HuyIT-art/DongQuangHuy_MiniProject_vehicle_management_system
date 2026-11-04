package com.example.vehicle_management_system.Service;

import com.example.vehicle_management_system.DTO.Response.PageResponse;
import com.example.vehicle_management_system.DTO.Response.VehicleResponse;
import com.example.vehicle_management_system.Enum.VehicleType;
import com.example.vehicle_management_system.Model.Vehicle;
import com.example.vehicle_management_system.Repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    //Lay thong tin phuong tien theo tu khoa
    public PageResponse<VehicleResponse> getPagedVehicles(Integer page, Integer size, String sortBy, Sort.Direction direction, VehicleType keyword) {
        if(page < 0){
            page = 0;
        }
        if(size < 0){
            size = 1;
        }
        Sort sort = Sort.unsorted();
        if(sortBy != null && direction != null) {
            sort = Sort.by(direction, sortBy);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VehicleResponse> vehicles = vehicleRepository.findAllByKeyword(keyword, pageable);
        PageResponse<VehicleResponse> pageResponse = PageResponse.<VehicleResponse>builder()
                .items(vehicles.getContent())
                .page(vehicles.getNumber())
                .size(vehicles.getSize())
                .totalPages(vehicles.getTotalPages())
                .totalItems(vehicles.getTotalElements())
                .isLast(vehicles.isLast())
                .build();

        return  pageResponse;
    }
    //Lay danh sach phuong tien
    public PageResponse<VehicleResponse> findAll(Integer page, Integer size, String sortBy, Sort.Direction direction) {
        if (page < 0) {
            page = 0;
        }
        if (size < 0) {
            size = 1;
        }
        Sort sort = Sort.unsorted();
        if (sortBy != null && direction != null) {
            sort = Sort.by(direction, sortBy);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VehicleResponse> vehicles = vehicleRepository.findAllVehicles(pageable);
        PageResponse<VehicleResponse> pageResponse = PageResponse.<VehicleResponse>builder()
                .items(vehicles.getContent())
                .page(vehicles.getNumber())
                .size(vehicles.getSize())
                .totalPages(vehicles.getTotalPages())
                .totalItems(vehicles.getTotalElements())
                .isLast(vehicles.isLast())
                .build();

        return  pageResponse;
    }
}
