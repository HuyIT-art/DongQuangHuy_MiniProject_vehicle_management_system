package com.example.vehicle_management_system.Controller;

import com.example.vehicle_management_system.DTO.Response.ZoneStatisticsResponse;
import com.example.vehicle_management_system.Repository.ZoneRepository;
import com.example.vehicle_management_system.Service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ZoneController {
    private final ZoneRepository zoneRepository;
    private final ZoneService zoneService;

    //Cach 1: Lay cac zone(su dung vong lap)
    @GetMapping("/v1/zones/stats")
    public ResponseEntity<List<ZoneStatisticsResponse>> getAllZoneStatistics() {
        List<ZoneStatisticsResponse> list = zoneService.getAllZoneStatistics();
        return ResponseEntity.ok().body(list);
    }

    //Cach 2: Lay cac zone su dung Class based projection
    @GetMapping("/v2/zones/stats")
    public ResponseEntity<List<ZoneStatisticsResponse>> getAllZone() {
        List<ZoneStatisticsResponse> list = zoneService.getAllZone();
        return ResponseEntity.ok().body(list);
    }
}
